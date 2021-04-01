package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceAlreadyExistException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void insert(@RequestBody TagDto tag) throws ResourceAlreadyExistException {
        tagService.insert(tag);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<TagDto> findAll() throws ResourceNotFoundException {
        return tagService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TagDto findById(@PathVariable int id) throws ResourceNotFoundException {
        return tagService.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) throws ResourceNotFoundException {
        tagService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody TagDto tag)
            throws ResourceNotFoundException, ResourceAlreadyExistException {
        tag.setId(id);
        tagService.update(tag);
    }
}
