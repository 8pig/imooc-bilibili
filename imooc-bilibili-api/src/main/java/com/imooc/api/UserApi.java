package com.imooc.api;


import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.service.UserService;
import com.imooc.bilibili.service.util.RSAUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserApi {

    @Resource
    private UserService userService;



    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        return JsonResponse.success( RSAUtil.getPublicKeyStr());
    }


    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return  JsonResponse.success("success");

    }

}
