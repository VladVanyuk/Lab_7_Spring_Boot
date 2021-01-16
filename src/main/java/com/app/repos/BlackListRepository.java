/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.repos;

import com.app.entity.BlackList;
import com.app.entity.BlackListKey;
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
public interface BlackListRepository  extends JpaRepository<BlackList, BlackListKey>{
    @Query(name="select b from BlackList b where b.lockingUser = ?1")
    List<BlackList> getByLockingUser(User lock);
    
     @Query(name="select b from BlackList b where b.user = ?1")
    List<BlackList> getByUser(User user);
    
    @Query(name="select b from BlackList b where b.user = :user and b.lockingUser = :lock")
    BlackList getByUserAndLockingUser(User user,User lock);
    
    
}
