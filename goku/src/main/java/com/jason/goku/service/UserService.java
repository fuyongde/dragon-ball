package com.jason.goku.service;

import com.jason.dragon.mapper.BeanMapper;
import com.jason.dragon.security.Digests;
import com.jason.dragon.security.Encodes;
import com.jason.goku.api.request.UserRequest;
import com.jason.goku.entity.Password;
import com.jason.goku.entity.User;
import com.jason.goku.repository.PasswordMapper;
import com.jason.goku.repository.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {

    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordMapper passwordMapper;

    @Transactional
    public int register(UserRequest userRequest) {
        User pojo = BeanMapper.map(userRequest, User.class);
        Password password = new Password();
        userMapper.insertSelective(pojo);
        password.setUserId(pojo.getId());
        password.setPassword(userRequest.getPassword());
        entryptPassword(password);
        passwordMapper.insertSelective(password);
        return pojo.getId();
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(Password password) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        password.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(password.getPassword().getBytes(), salt, HASH_INTERATIONS);
        password.setPassword(Encodes.encodeHex(hashPassword));
    }
}
