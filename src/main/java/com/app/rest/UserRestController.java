/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.rest;

import com.app.DTO.BlackListDTO;
import com.app.DTO.PostDTO;
import com.app.DTO.UserDTO;
import com.app.entity.BlackList;
import com.app.entity.Post;
import com.app.entity.User;
import com.app.exceptions.NoSuchPostException;
import com.app.exceptions.NoSuchUserException;
import com.app.service.BlackListService;
import com.app.service.PostService;
import com.app.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Root
 */
@RestController
@RequestMapping(value = "api")
public class UserRestController {
    
    @Autowired UserService userService;
    @Autowired PostService postService;
    @Autowired  BlackListService blackService;
    
    // повертає всіх користувачів
    @RequestMapping(value = "/user", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> list = userService.getAll();
        List<UserDTO> listDTO = new ArrayList<>();
         Link link = linkTo(methodOn(UserRestController.class).getAllUsers()).withSelfRel();
        for(User user :list){
            Link selfLink = new Link(link.getHref() + "/" + user.getUserId()).withSelfRel();
            UserDTO userDTO = new UserDTO(user, selfLink);
            listDTO.add(userDTO);
        }
        return   new ResponseEntity<>(listDTO, HttpStatus.OK);
    }
    // повертає користувача по id
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) throws NoSuchUserException {
         User user =  userService.getUserById(id);
        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(id)).withSelfRel();
        UserDTO userDTO = new UserDTO(user, selfLink);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }
    // список користувачів яких id заблокував
    @RequestMapping(value = "/user/{id}/blockedUser", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<BlackListDTO>> getBlockedUsers(@PathVariable int id) throws NoSuchUserException {
        User user = userService.getUserById(id);
        List<BlackList> list = user.getBlockedUsers().stream().collect(Collectors.toList());
        List<BlackListDTO> listDTO = new ArrayList<>();
        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(id)).withSelfRel();
        for(BlackList b:list){
            Link link = new Link(selfLink.getHref() + "/blockedUser").withRel("blockedUser");
            BlackListDTO bDTO = new BlackListDTO(b,link);
            listDTO.add(bDTO);
        }
        return new ResponseEntity<>(listDTO, HttpStatus.OK);

    }
    
    
    
    // заблокувати користувача pid користувачем id
    @RequestMapping(value = "/user/{id}/blockingUser/{pid}", method = RequestMethod.POST,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity blockUser(@PathVariable int id,@PathVariable int pid) throws NoSuchUserException {
        User userBlock = userService.getUserById(id);
        User userLock = userService.getUserById(pid);
        blackService.blockUser(userLock,userBlock);
        //userBlock = userService.getUserById(id);
        return new ResponseEntity(HttpStatus.OK); 
//new ResponseEntity<>(userBlock.getBlockedUsers().stream().collect(Collectors.toList()), HttpStatus.OK);

    }
    // розблокувати користувача pid користувачем id
    @RequestMapping(value = "/user/{id}/blockingUser/{pid}", method = RequestMethod.DELETE,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity delBlockedUser(@PathVariable int id,@PathVariable int pid) throws NoSuchUserException {
        User userBlock = userService.getUserById(id);
        User userLock = userService.getUserById(pid);
        blackService.unblockUser(userLock,userBlock);
        return new ResponseEntity(HttpStatus.OK);
//               new ResponseEntity<>(userBlock.getBlockedUsers().stream().collect(Collectors.toList()), HttpStatus.OK);

    }
    
    // список користуваячів які заблокували користувача id
    @RequestMapping(value = "/user/{id}/lockingUser", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<BlackListDTO>> getLockingUsers(@PathVariable int id) throws NoSuchUserException {
        User user = userService.getUserById(id);
        List<BlackList> list = user.getLockingUsers().stream().collect(Collectors.toList());
        List<BlackListDTO> listDTO = new ArrayList<>();
        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(id)).withSelfRel();
        for(BlackList b:list){
            Link link = new Link(selfLink.getHref() + "/lockingUser").withRel("lockingUser");
            BlackListDTO bDTO = new BlackListDTO(b,link);
            listDTO.add(bDTO);
        }
        return new ResponseEntity<>(listDTO, HttpStatus.OK);

    }
     // пости користувача id
    @RequestMapping(value = "/user/{id}/post", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<PostDTO>> getPostUserById(@PathVariable int id) throws NoSuchUserException {
        List<Post> list = userService.getUserById(id).getPosts().stream().collect(Collectors.toList());
        List<PostDTO> listDTO = new ArrayList<>();
        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(id)).withSelfRel();
        for (Post post :list){
            Link link = new Link(selfLink.getHref() + "/post/" + post.getPostId()).withRel("post");
            PostDTO postDTO = new PostDTO(post, link);
            listDTO.add(postDTO);
        }
    
        return new ResponseEntity<>(listDTO, HttpStatus.OK);

    }
    // додати пост користувачу id
    @RequestMapping(value = "/user/{id}/post", method = RequestMethod.POST,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<PostDTO> addPostUserById(@RequestBody Post post,
            @PathVariable int id) throws NoSuchUserException {
        User user = userService.getUserById(id);
        post.setUser(user);
        post = postService.createPost(user,post);
        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(id)).withSelfRel();
        Link link = new Link(selfLink.getHref() + "/post/" + post.getPostId()).withRel("post");
        PostDTO postDTO = new PostDTO(post, link);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);

    }
    
    
    // отримати пост pid коритсувача id
     @RequestMapping(value = "/user/{id}/post/{pid}", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<PostDTO> getPostByIdUserById(
        @PathVariable int id,@PathVariable int pid) throws NoSuchUserException,NoSuchPostException {
        User user = userService.getUserById(id);
        Post post = postService.getPostsByUserAndId(user, pid);
         Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(id)).withSelfRel();
        Link link = new Link(selfLink.getHref() + "/post/" + post.getPostId()).withSelfRel();
        PostDTO postDTO = new PostDTO(post, link);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);

    }
    // обнововити пост pid користувача id
    @RequestMapping(value = "/user/{id}/post/{pid}", method = RequestMethod.PUT,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<PostDTO> editPostUserById(@RequestBody Post post,
        @PathVariable int id,@PathVariable int pid) throws NoSuchUserException,NoSuchPostException {
        Post p = postService.updatePost(post, pid);
        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(id)).withSelfRel();
        Link link = new Link(selfLink.getHref() + "/post/" + post.getPostId()).withSelfRel();
        PostDTO postDTO = new PostDTO(p, link);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);

    }
    
    // видали пост pid користувача id
    @RequestMapping(value = "/user/{id}/post/{pid}", method = RequestMethod.DELETE,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity delPostUserById(
            @PathVariable int id,@PathVariable int pid) throws NoSuchUserException,NoSuchPostException{
        postService.deletePostById(pid);
        return new ResponseEntity(HttpStatus.OK);

    }
    
    
    // додати  користувача
    @RequestMapping(value = "/user",method = RequestMethod.POST)
            //,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        user = userService.createUser(user);
        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(user.getUserId())).withSelfRel();
        UserDTO userDTO = new UserDTO(user, selfLink);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
     
    }
    // обновити користувача
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user,@PathVariable int id) {
        User u = userService.getUserById(id);
        u = userService.update(user, id);
        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(id)).withSelfRel();
        UserDTO userDTO = new UserDTO(u, selfLink);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
        
    }

    // видалити користувачав
     @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE,produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity delUserById(@PathVariable int id) throws NoSuchUserException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    
    
    
    
}
