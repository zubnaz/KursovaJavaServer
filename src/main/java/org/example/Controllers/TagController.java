package org.example.Controllers;

import org.example.Entities.TagEntity;
import org.example.Repositories.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagController {

    private TagRepository tagRepository;

    public TagController(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public ResponseEntity<List<TagEntity>> get(){
        List<TagEntity> list = tagRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
