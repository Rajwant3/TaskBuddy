package com.task.buddy.service;

import java.util.List;

import com.task.buddy.model.Role;

public interface RoleService {
    Role createRole(Role role);

    List<Role> findAll();
}
