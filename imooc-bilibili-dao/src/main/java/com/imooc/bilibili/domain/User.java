package com.imooc.bilibili.domain;



import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;

    private  String phone;

    private String email;

    private String password;

    private String salt;

    private Date updateTime;

    private Date createTime;


    private UserInfo userInfo;



}
