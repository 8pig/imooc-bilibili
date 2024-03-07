package com.imooc.api;


import com.imooc.bilibili.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DemoApi {


    @Resource
    private DemoService demoService;




    @GetMapping("/query")
    public Long query(Long id){
        return demoService.query(id);
    }

}
