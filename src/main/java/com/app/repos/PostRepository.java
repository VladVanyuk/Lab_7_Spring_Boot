/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.repos;

import com.app.entity.Post;
import com.app.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Root
 */
@Repository
public interface PostRepository extends JpaRepository<Post,Integer>{
    @Query(name="select p from Post where p.user = ?1 order by p.postId")
    List<Post> findByUser(User user);
    
    @Query(name="select p from Post where p.user = ?1 and p.postId = ?2")
    Post getByUserAndPostId(User user,int postId);
    
    @Query(name="delete p from Post where p.user = ?1")
    void deleteByUser(User user);
}
