package com.jason.goku.service;

import com.google.common.collect.Sets;
import com.jason.dragon.mapper.BeanMapper;
import com.jason.dragon.security.Digests;
import com.jason.dragon.security.Encodes;
import com.jason.goku.api.request.UserRequest;
import com.jason.goku.entity.Password;
import com.jason.goku.entity.User;
import com.jason.goku.exception.ServiceException;
import com.jason.goku.repository.PasswordMapper;
import com.jason.goku.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordMapper passwordMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User register(UserRequest userRequest) {
        User pojo = BeanMapper.map(userRequest, User.class);
        Password password = new Password();
        userMapper.insertSelective(pojo);
        password.setUserId(pojo.getId());
        password.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        passwordMapper.insertSelective(password);
        return pojo;
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

    public boolean login(UserRequest userRequest) {
        User user = Optional
                .ofNullable(userMapper.findByUsername(userRequest.getUsername()))
                .orElseThrow(() -> new ServiceException("用户不存在"));
        Password password = Optional
                .ofNullable(passwordMapper.findByUserId(user.getId()))
                .orElseThrow(() -> new ServiceException("用户不存在"));
        return passwordEncoder.matches(userRequest.getPassword(), password.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional
                .ofNullable(userMapper.findByUsername(username))
                .orElseThrow(() -> new ServiceException("用户不存在"));
        Password password = Optional
                .ofNullable(passwordMapper.findByUserId(user.getId()))
                .orElseThrow(() -> new ServiceException("用户不存在"));
        org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(
                user.getUsername(), password.getPassword(), Sets.newHashSet(new SimpleGrantedAuthority("admin"))
        );
        return u;
    }
}
