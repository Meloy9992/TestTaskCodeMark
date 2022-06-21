package com.example.TestTask.dao;

import com.example.TestTask.TestTaskXmlService.UserXml;
import com.example.TestTask.models.Users;

import java.util.List;

public interface UsersDao {

    List<UserXml> getListUsersWithoutRoles();

    Users getUserByLoginWithRoles(String login);

    boolean DeleteUserByLogin(String login);

    void addNewUserWithRoles(Users user);

    boolean editUser(Users user);


}
