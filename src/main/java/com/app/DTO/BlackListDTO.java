/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.DTO;

import com.app.entity.BlackList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Root
 */
@Getter
@Setter
public class BlackListDTO extends ResourceSupport{
    BlackList blackList;

    public BlackListDTO(BlackList blackList,Link selfLink)
    {
        this.blackList = blackList;
        add(selfLink);
    }
    
    
}
