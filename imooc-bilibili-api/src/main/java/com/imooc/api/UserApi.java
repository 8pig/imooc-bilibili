package com.imooc.api;


import com.imooc.api.support.UserSupport;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.service.UserService;
import com.imooc.bilibili.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserApi {

    @Resource
    private UserService userService;

    @Autowired
    private UserSupport userSupport;


    @GetMapping("users")
    public JsonResponse getUserInfo() {
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        return new JsonResponse<>(user);    }

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        return JsonResponse.success( RSAUtil.getPublicKeyStr());
    }


    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return  JsonResponse.success("success");
    }

    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception {
        String token = userService.login(user);
        return  new JsonResponse<>(token);
    }

}
