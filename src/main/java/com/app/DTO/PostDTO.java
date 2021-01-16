/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.DTO;

import com.app.entity.Post;
import com.app.exceptions.NoSuchPostException;
import com.app.rest.PostRestController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 *
 * @author Root
 */
@Getter
@Setter
public class PostDTO   extends ResourceSupport {
    Post post;

    public PostDTO(Post post,Link selfLink) throws NoSuchPostException
    {
        this.post = post;
        add(selfLink);
        
    }
    
    
}
