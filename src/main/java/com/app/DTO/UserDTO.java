/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.DTO;

import com.app.entity.User;
import com.app.exceptions.NoSuchPostException;
import com.app.exceptions.NoSuchUserException;
import com.app.rest.PostRestController;
import com.app.rest.UserRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author Root
 */
public class UserDTO  extends ResourceSupport {
    User user;

    public UserDTO(User user, Link selfLink) throws NoSuchUserException{
        this.user = user;
        add(selfLink);
        add(linkTo(methodOn(UserRestController.class).getPostUserById(user.getUserId())).withRel("post"));
        add(linkTo(methodOn(UserRestController.class).getBlockedUsers(user.getUserId())).withRel("blockedUser"));
        add(linkTo(methodOn(UserRestController.class).getLockingUsers(user.getUserId())).withRel("lockingUsers"));
    }

    
    
    
}
