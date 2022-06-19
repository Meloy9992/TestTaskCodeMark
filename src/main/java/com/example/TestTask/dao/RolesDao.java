package com.example.TestTask.dao;

import com.example.TestTask.models.Roles;

import java.util.List;

public interface RolesDao {

    boolean EditListRoles(List<Roles> rolesList);

    boolean AddNewRoles(Roles roles);
}
