package com.example.springwebtemplate.dao;

import java.util.List;

import com.example.springwebtemplate.dbo.UserDbo;

public interface UserDao extends AbstractDao<UserDbo, Long> {
    void saveUser(UserDbo user);
    UserDbo findById(long userId);
    UserDbo findByEmail(String email);
    UserDbo findUser(String userName);
    UserDbo findByAuthorizationId(String authorizationId);
    List<UserDbo> getAllUsers();
}
