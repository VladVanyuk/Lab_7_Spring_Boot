/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.rest;

import com.app.DTO.PostDTO;
import com.app.entity.Post;
import com.app.service.PostService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author Root
 */
@RestController
@RequestMapping("/api")
public class PostRestController {

    @Autowired
    PostService postService;

    // повертає всі пости
    @GetMapping("/post")
    public ResponseEntity<List<PostDTO>> list() {
        List<Post> postList = postService.getAll();
        Link link = linkTo(methodOn(PostRestController.class).list()).withSelfRel();
        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post entity : postList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getPostId()).withSelfRel();
            PostDTO dto = new PostDTO(entity, selfLink);
            postsDTO.add(dto);
        }
        return new ResponseEntity<>(postsDTO, HttpStatus.OK);

    }

    // повертає пост по id
    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> get(@PathVariable int id) {
        Post post = postService.getPostById(id);
        Link selfLink = linkTo(methodOn(PostRestController.class).get(id)).withSelfRel();
        PostDTO postDTO = new PostDTO(post, selfLink);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    // видалає пост по id
    @DeleteMapping("/post/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        postService.deletePostById(id);;
        return new ResponseEntity(HttpStatus.OK);
    }

}
