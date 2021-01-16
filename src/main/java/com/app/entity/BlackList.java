/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author Root
 */
@Entity
@Table(name="blacklist")
@Getter
@Setter
@IdClass(BlackListKey.class)
public class BlackList implements Serializable {
    @Id
    
    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonBackReference
    @JoinColumn(name = "user_id",nullable = false)
    User user;
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonBackReference
    @JoinColumn(name = "locking_user_id",nullable = false)
    User lockingUser;
    
    @Column(name="locking_time",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date lockingTime = new Date();

    public BlackList() {
    }

    
    
    
}
