/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.service;

import com.app.entity.Post;
import com.app.entity.User;
import com.app.exceptions.NoSuchPostException;
import com.app.exceptions.NoSuchUserException;
import com.app.repos.PostRepository;
import com.app.repos.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Root
 * сервіс по роботі з таблицею Post
 */
@Service
public class PostService {
    @Autowired PostRepository postRepository;
     @Autowired UserRepository userRepository;
    
    
    
    @Transactional
    public Post createPost(User user,Post post){
        post.setUser(user);
        if (post.getDate()==null) post.setDate(new Date());
        post = postRepository.save(post);
        return post;
    }
    
    @Transactional
    public Post updatePost(Post post,int id){
        Optional<Post> p = postRepository.findById(id);
        if (!p.isPresent())  throw new NoSuchPostException();
        Post p_ = p.get();
        if (post.getDate()==null) post.setDate(new Date());
        p_.setDate(post.getDate());
        p_.setDescription(post.getDescription());
        p_.setPostType(post.getPostType());
        post = postRepository.save(p_);
        return post;
    }
    
    
    
    public List<Post> getAll(){
        return postRepository.findAll();
    }
    
    public Post getPostById(int id){
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) throw new NoSuchPostException("Пост не знайдено");
        return post.get();
    }
    
    public List<Post> getPostsByUser(User user){
        return postRepository.findByUser(user);
    }
     
    public Post getPostsByUserAndId(User user, int id){
        Post post = postRepository.getByUserAndPostId(user, id);
        System.out.println(post.getPostId());
        if (post==null)throw new NoSuchPostException("Пост не знайдено");
        return post;
    }
     
    @Transactional
    public void deletePost(Post post){
        postRepository.delete(post);
    }
    @Transactional
    public void deletePostById(int id){
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) throw new NoSuchPostException("Пост не знайдено");
        postRepository.delete(post.get());
    }
    
    @Transactional
    public void deleteAllPostByList(List<Post> post){
        postRepository.deleteAll(post);
    }
    
    @Transactional
    public void deletePostByUser(User user){
        postRepository.deleteByUser(user);
    }
    
    
    @Transactional
    public void deleteAll(){
        postRepository.deleteAll();
    }
    
    @Transactional
    public Post save(Post post){
        return   postRepository.save(post);
    }
}
