package com.imooc.bilibili.service;

import com.imooc.bilibili.Exception.ConditionException;
import com.imooc.bilibili.dao.UserDao;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.domain.constant.UserConstant;
import com.imooc.bilibili.service.util.MD5Util;
import com.imooc.bilibili.service.util.RSAUtil;
import com.imooc.bilibili.service.util.TokenUtil;
import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserDao userDao;


    public void addUser(User user) {
        String phone = user.getPhone();
        if(StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号不可为空");
        }
        User dbUser = this.getUserByPhone(phone);
        if(dbUser != null){
            throw new ConditionException("该手机号已注册");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());

        String password = user.getPassword();
        log.info(password);
        String rawPassword;

        try {
            rawPassword = RSAUtil.decrypt(password);
        }catch (Exception e){
            throw new ConditionException("密码解密失败!");
        }

        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");

        user.setSalt(salt);
        user.setPassword(md5Password);
        user.setCreateTime(now);

        userDao.addUser(user);

        // 添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);

        userDao.addUserInfo(userInfo);


    }




    public User getUserByPhone(String phone){
        return  userDao.getUserByPhone(phone);
    }

    public User getUserInfo(Long userId) {
        User user = userDao.getUserById(userId);
        UserInfo userInfo = userDao.getUserInfoByUserId(userId);
        user.setUserInfo(userInfo);
        return user;
    }

    public String login(User user) throws Exception {
        String phone = user.getPhone();

        if(StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号不可为空");
        }

        User dbUser = this.getUserByPhone(phone);
        if(dbUser == null){
            throw new ConditionException("当前手机号不存在");
        }

        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败!");
        }
        String salt = dbUser.getSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");
        if(!md5Password.equals(dbUser.getPassword())){
            throw new ConditionException("密码错误!");
        }
        return TokenUtil.genrateToken(dbUser.getId());

    }
}
