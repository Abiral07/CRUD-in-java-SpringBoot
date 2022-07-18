package com.RESTfullCRUD.BasicCRUD.service;

import com.RESTfullCRUD.BasicCRUD.entity.Role;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRole();

    Role addNewRole(Role role) throws CustomException;

    Role updateRole(Long id,Role role) throws CustomException;

    Set<String> addRoleToUSer(Long id, Set<String> roles);
}
