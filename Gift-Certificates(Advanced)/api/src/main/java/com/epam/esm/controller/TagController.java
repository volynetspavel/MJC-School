package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.hateoas.TagHateoas;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Class is used to send requests from the client to the service layer for tag entity.
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    private TagService tagService;
    private TagHateoas tagHateoas;

    @Autowired
    public TagController(TagService tagService, TagHateoas tagHateoas) {
        this.tagService = tagService;
        this.tagHateoas = tagHateoas;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TagDto insert(@RequestBody TagDto tag) throws ResourceAlreadyExistException, ResourceNotFoundException {
        TagDto tagDto = tagService.insert(tag);
        tagHateoas.addLinksForTagDto(tagDto);
        return tagDto;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CollectionModel<TagDto> findAll(@RequestParam Map<String, String> params) throws ResourceNotFoundException {
        List<TagDto> tagList = tagService.findAll(params);
        return tagHateoas.addLinksForListOfTagDto(tagList);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TagDto findById(@PathVariable("id") int id) throws ResourceNotFoundException {
        TagDto tagDto = tagService.findById(id);
        tagHateoas.addLinksForTagDto(tagDto);
        return tagDto;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        tagService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TagDto update(@PathVariable("id") int id, @RequestBody TagDto tag)
            throws ResourceNotFoundException, ResourceAlreadyExistException {
        tag.setId(id);
        TagDto tagDto = tagService.update(tag);
        tagHateoas.addLinksForTagDto(tagDto);
        return tagDto;
    }

    @GetMapping("/popular")
    public TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() throws ResourceNotFoundException {
        TagDto tagDto = tagService.getMostPopularTagOfUserWithHighestCostOfAllOrders();
        tagHateoas.addLinksForTagDto(tagDto);
        return tagDto;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{id}")
    public TagDto findTagBYUserIdWithHighestCostOfAllOrders(@PathVariable("id") int userId)
            throws ResourceNotFoundException {
        TagDto tagDto = tagService.findTagBYUserIdWithHighestCostOfAllOrders(userId);
        tagHateoas.addLinksForTagDto(tagDto);
        tagHateoas.addLinksForUser(tagDto, userId);
        return tagDto;
    }
}
