package org.example.Controllers;

import org.example.Entities.CategoryEntity;
import org.example.Entities.PostEntity;
import org.example.Repositories.CategoryRepository;
import org.example.Repositories.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private PostRepository postRepository;

    public PostController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<List<PostEntity>> get(){
        List<PostEntity> list = postRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
