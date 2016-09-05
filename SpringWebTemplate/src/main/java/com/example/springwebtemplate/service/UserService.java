package com.example.springwebtemplate.service;

import java.util.List;

import com.example.springwebtemplate.controller.response.UserWebModel;
import com.example.springwebtemplate.dbo.UserDbo;

public interface UserService extends AbstractService<UserDbo>{

    UserDbo findById(long staffId);
    void saveUser(UserDbo user);
    boolean updateUser(UserWebModel staffWebModel);
    void deleteUser(long userId);
    void deleteUser(UserDbo userWillBeDeleted);
    UserDbo findUser(String user);
    UserDbo findUserByEmail(String email);
    UserDbo findByAuthorizationId(String authorizationId);
    List<UserDbo> getAllUsers();
    UserDbo isUserCredentialsValid(String uname, String password);
}
