package com.jason.goku.rest;

import com.jason.dragon.utils.BeanValidators;
import com.jason.goku.api.request.UserRequest;
import com.jason.goku.entity.User;
import com.jason.goku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private Validator validator;

    @PostMapping
    public User register(@RequestBody UserRequest userRequest) {
        BeanValidators.validateWithException(validator, userRequest);
        userService.register(userRequest);
        return null;
    }

}
