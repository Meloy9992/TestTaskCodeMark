package com.example.TestTask.dao;

import com.example.TestTask.models.Roles;
import com.example.TestTask.models.Users;

import java.util.List;

public interface RolesDao {

    boolean EditListRoles(List<Roles> rolesList);

    boolean AddNewRoles(Roles roles);

    boolean DeleteRole(Users users);

    Roles getRoles(long id);

    Long getLastId();
}
