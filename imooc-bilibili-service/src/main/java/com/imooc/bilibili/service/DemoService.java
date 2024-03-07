package com.imooc.bilibili.service;


import com.imooc.bilibili.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DemoService {

    @Resource
    private DemoDao demoDao;


   public Long query(Long id){
       return demoDao.query(id);
   }
}
