package com.epam.esm.service.impl;

import com.epam.esm.constant.CodeException;
import com.epam.esm.constant.TableColumn;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.security.SecurityUtil;
import com.epam.esm.service.TagService;
import com.epam.esm.validation.PaginationValidator;
import com.epam.esm.validation.SecurityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is an implementation of TagService.
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends TagService {

    private TagDao tagDao;
    private UserDao userDao;
    private TagMapper tagMapper;
    private PaginationValidator paginationValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, UserDao userDao, TagMapper tagMapper, PaginationValidator paginationValidator) {
        this.tagDao = tagDao;
        this.userDao = userDao;
        this.tagMapper = tagMapper;
        this.paginationValidator = paginationValidator;
    }

    @Transactional
    @Override
    public TagDto insert(TagDto tagDto) throws ResourceAlreadyExistException {
        Optional<Tag> tagWithSameName = tagDao.findByName(tagDto.getName());
        if (tagWithSameName.isPresent()) {
            throw new ResourceAlreadyExistException(CodeException.RESOURCE_ALREADY_EXIST, tagDto.getName());
        }

        Tag newTag = tagDao.save(tagMapper.toEntity(tagDto));
        return tagMapper.toDto(newTag);
    }

    @Transactional
    @Override
    public void delete(int id) throws ResourceNotFoundException {
        Optional<Tag> tag = tagDao.findById(id);
        checkEntityOnNull(tag, id);
        tagDao.delete(tag.get());
    }

    @Transactional
    @Override
    public TagDto update(TagDto newTagDto) throws ResourceNotFoundException, ResourceAlreadyExistException {
        int id = newTagDto.getId();
        String name = newTagDto.getName();

        Optional<Tag> oldTag = tagDao.findById(id);
        checkEntityOnNull(oldTag, id);

        Optional<Tag> tagWithSameName = tagDao.findByName(name);
        if (tagWithSameName.isPresent()) {
            throw new ResourceAlreadyExistException(CodeException.RESOURCE_ALREADY_EXIST, name);
        }
        oldTag.get().setName(name);
        return tagMapper.toDto(oldTag.get());
    }

    @Override
    public List<TagDto> findAll(Map<String, String> params) throws ValidationParametersException {
        int limit = (int) tagDao.count();
        int offset = 0;

        if (paginationValidator.validatePaginationParameters(params)) {
            limit = paginationValidator.getLimit();
            offset = paginationValidator.getOffset();
        }

        Sort sort = Sort.by(TableColumn.ID);
        List<Tag> tags = tagDao.findAll(PageRequest.of(offset, limit, sort)).toList();
        return migrateListFromEntityToDto(tags);
    }

    @Override
    public TagDto findById(int id) throws ResourceNotFoundException {
        Optional<Tag> tag = tagDao.findById(id);
        checkEntityOnNull(tag, id);
        return tagMapper.toDto(tag.get());
    }

    @Override
    public TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() throws ResourceNotFoundException {
        Optional<Tag> tag = tagDao.getMostPopularTagOfUserWithHighestCostOfAllOrders();
        if (!tag.isPresent()) {
            throw new ResourceNotFoundException(CodeException.RESOURCE_NOT_FOUND_WITHOUT_ID);
        }
        return tagMapper.toDto(tag.get());
    }

    @Override
    public TagDto findTagBYUserIdWithHighestCostOfAllOrders(int userId) throws ResourceNotFoundException {
        if (SecurityValidator.isCurrentUserHasRoleUser()) {
            userId = Objects.requireNonNull(SecurityUtil.getJwtUserId());
        }
        Optional<User> user = userDao.findById(userId);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException(CodeException.RESOURCE_NOT_FOUND, userId);
        }
        Optional<Tag> tag = tagDao.findTagBYUserIdWithHighestCostOfAllOrders(userId);
        if (!tag.isPresent()) {
            throw new ResourceNotFoundException(CodeException.RESOURCE_NOT_FOUND_BY_USER_ID, userId);
        }
        return tagMapper.toDto(tag.get());
    }

    private List<TagDto> migrateListFromEntityToDto(List<Tag> tags) {
        return tags.stream()
                .map(tag -> tagMapper.toDto(tag))
                .collect(Collectors.toList());
    }
}
