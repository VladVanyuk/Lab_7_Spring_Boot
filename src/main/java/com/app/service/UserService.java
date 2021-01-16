/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.service;

import com.app.entity.User;
import com.app.exceptions.ExistBlockedUserException;
import com.app.exceptions.ExistsPostUserExsception;
import com.app.exceptions.NoSuchUserException;
import com.app.repos.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Root сервіс по роботі з таблицею User
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // створення нового користувача

    @Transactional
    public User createUser(User user) {
        if (user.getLastActivity()==null) {user.setLastActivity(new Date());};
        user = userRepository.save(user);
        return user;
    }

    // отримати всіх користувачів
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // отримати користувача по ID
    public User getUserById(int id) throws NoSuchUserException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())throw new NoSuchUserException("Користувача не знайдено");
        return  user.get();
    }

    // видалити користувача
    @Transactional
    public void deleteUser(int id) throws NoSuchUserException, ExistsPostUserExsception,ExistBlockedUserException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) throw new NoSuchUserException();
        //if (user.get().getPosts().size()>0) throw new ExistsPostUserExsception("У користувача є пости!");
        userRepository.delete(user.get());
    }

    //видалити всіх користувачів
    @Transactional
    public void deleteUserAll() {
        userRepository.deleteAll();
    }

   // обновить пользователя
    @Transactional
    public User update(User user, int id) throws NoSuchUserException {
        Optional<User> u = userRepository.findById(id);
        if (!u.isPresent()) {
            throw new NoSuchUserException("Користувача не знайдено");
        }
        if (user.getLastActivity()==null) {user.setLastActivity(new Date());};
        User user_ = u.get();
        user_.setNickName(user.getNickName());
        user_.setFoto(user.getFoto());
        user_.setLastActivity(user.getLastActivity());
        return userRepository.save(user_);
    }

   

}
