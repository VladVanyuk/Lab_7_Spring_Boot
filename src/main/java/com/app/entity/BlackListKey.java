/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.entity;

import java.io.Serializable;
import javax.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Root
 */
 
@EqualsAndHashCode
@Getter
@Setter
public class BlackListKey implements Serializable {
    int user;
    int lockingUser;

    public BlackListKey() {
    }
    

    
    
    
}
