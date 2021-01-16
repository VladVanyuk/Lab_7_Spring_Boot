/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.service;

import com.app.entity.BlackList;
import com.app.entity.BlackListKey;
import com.app.entity.User;
import com.app.repos.BlackListRepository;
import com.app.repos.UserRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Root
 */
@Service
public class BlackListService {
   @Autowired BlackListRepository blackRepository;
   @Autowired UserRepository userRepository;
   
   
   // заблокувати користувача
   @Transactional
   public void blockUser(User locking,User user ){
       BlackList blackList = new BlackList();
       blackList.setUser(user);
       blackList.setLockingUser(locking);
       blackList.setLockingTime(new Date());
       blackRepository.save(blackList);
   }
   
   // розблокувати користувача 
   @Transactional
   public void unblockUserFull(User lock){
       List<BlackList> blackList = blackRepository.getByLockingUser(lock);
       blackRepository.deleteAll(blackList);
   }
   // позблокувати користувача lock якого заблокував user
   @Transactional
   public void unblockUser(User lock,User user){
       BlackList blackList = blackRepository.getByUserAndLockingUser(user, lock);
       blackRepository.delete(blackList);
   }
   
   // отримати заблокованих користувачів
   public List<BlackList> getBlackList(){
       return blackRepository.findAll();
   }
   
   
}
