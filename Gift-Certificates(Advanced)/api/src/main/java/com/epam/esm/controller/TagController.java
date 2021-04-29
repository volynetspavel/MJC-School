package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.ValidationParametersException;
import com.epam.esm.hateoas.TagHateoas;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;


/**
 * Class is used to send requests from the client to the service layer for tag entity.
 */
@RestController
@RequestMapping("/tag")
@Validated
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
    public TagDto insert(@Valid @RequestBody TagDto tag)
            throws ResourceAlreadyExistException, ValidationParametersException, ResourceNotFoundException, ServiceException {
        TagDto tagDto = tagService.insert(tag);
        tagHateoas.addLinksForTagDto(tagDto);
        return tagDto;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CollectionModel<TagDto> findAll(@RequestParam Map<String, String> params)
            throws ValidationParametersException, ResourceNotFoundException {
        List<TagDto> tagList = tagService.findAll(params);
        return tagHateoas.addLinksForListOfTagDto(tagList);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TagDto findById(@PathVariable("id")
                           @Min(value = 1, message = "Enter id more than one.")
                                   int id) throws ResourceNotFoundException, ValidationParametersException {
        TagDto tagDto = tagService.findById(id);
        tagHateoas.addLinksForTagDto(tagDto);
        return tagDto;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")
                       @Min(value = 1, message = "Enter id more than one.")
                               int id) throws ResourceNotFoundException {
        tagService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TagDto update(@PathVariable("id")
                         @Min(value = 1, message = "Enter id more than one.") int id,
                         @Valid @RequestBody TagDto tag)
            throws ResourceNotFoundException, ResourceAlreadyExistException, ValidationParametersException, ServiceException {
        tag.setId(id);
        TagDto tagDto = tagService.update(tag);
        tagHateoas.addLinksForTagDto(tagDto);
        return tagDto;
    }

    @GetMapping("/popular")
    public TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() throws ResourceNotFoundException, ValidationParametersException {
        TagDto tagDto = tagService.getMostPopularTagOfUserWithHighestCostOfAllOrders();
        tagHateoas.addLinksForTagDto(tagDto);
        return tagDto;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{id}")
    public TagDto findTagBYUserIdWithHighestCostOfAllOrders(@PathVariable("id")
                                                            @Min(value = 1, message = "Enter id more than one.")
                                                                    int userId)
            throws ResourceNotFoundException, ValidationParametersException {
        TagDto tagDto = tagService.findTagBYUserIdWithHighestCostOfAllOrders(userId);
        tagHateoas.addLinksForTagDto(tagDto);
        tagHateoas.addLinksForUser(tagDto, userId);
        return tagDto;
    }
}
