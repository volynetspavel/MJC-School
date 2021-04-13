package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * Class is used to send requests from the client to the service layer for tag entity.
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TagDto insert(@RequestBody TagDto tag) throws ResourceAlreadyExistException {
        return tagService.insert(tag);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TagDto> findAll() throws ResourceNotFoundException {
        return tagService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TagDto findById(@PathVariable("id") int id) throws ResourceNotFoundException {
        return tagService.findById(id);
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
        return tagService.update(tag);
    }
}
