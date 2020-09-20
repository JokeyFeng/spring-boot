package com.jokey.bingo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author JokeyFeng
 * date:2019/3/21
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:
 */
@Entity
@Getter
@Setter
@Table(name = "bad")
public class User implements Serializable {

    private static final long serialVersionUID = -9192078693649134881L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String pass;

    @Column
    private String mobile;

    @Column
    private Integer age;

    @Column
    private Boolean status;
}
