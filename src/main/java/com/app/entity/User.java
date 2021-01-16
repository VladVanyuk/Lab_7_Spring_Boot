/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Root
 */
@Entity
@Table(name="user")
@Getter
@Setter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")        
    int userId;
    @Column(name = "nick_name", length = 45)
    String nickName;
    @Column(length = 255)
    String foto;
    @Column(name = "last_activity")
    Date lastActivity;
    
    @OneToMany(mappedBy = "user",cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    //@JsonBackReference
    @JsonIgnore
    Set<Post> posts;
    
    @OneToMany(mappedBy = "user",cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    //@JsonBackReference
    @JsonIgnore
    Set<BlackList> blockedUsers;
    
    @OneToMany( mappedBy = "lockingUser",cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    //@JsonBackReference
    @JsonIgnore        
    Set<BlackList> lockingUsers;

    public User() {
    }
    
    

}
