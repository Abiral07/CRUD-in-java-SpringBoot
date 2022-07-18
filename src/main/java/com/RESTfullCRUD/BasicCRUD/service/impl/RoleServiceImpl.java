package com.RESTfullCRUD.BasicCRUD.service.impl;

import com.RESTfullCRUD.BasicCRUD.entity.Role;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;
import com.RESTfullCRUD.BasicCRUD.repository.RoleRepository;
import com.RESTfullCRUD.BasicCRUD.repository.UserRepository;
import com.RESTfullCRUD.BasicCRUD.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role addNewRole(Role role) throws CustomException {
        if(roleRepository.findByRoleName(role.getRoleName()) != null){
            throw new CustomException("Role already exist. please choose different name");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id,Role role) throws CustomException {
        Role role1 = roleRepository.findDistinctByRoleId(id);
        if(role1 != null){
            if(Objects.nonNull(role.getRoleName()) && !"".equalsIgnoreCase(role.getRoleName()))
                role1.setRoleName(role.getRoleName());
            if(Objects.nonNull(role.getRoleDescription()) && !"".equalsIgnoreCase(role.getRoleDescription()))
                role1.setRoleDescription(role.getRoleDescription());
            return roleRepository.save(role1);
        }
        throw new CustomException("Role not found");
    }

    @Override
    public Set<String> addRoleToUSer(Long id, Set<String> roles) {
        User user = userRepository.findById(id).get();
        roles.forEach(role->{
            role = role.toUpperCase();
            Role roleFromDb = roleRepository.findByRoleName(role);
            if(roleFromDb != null) {
                user.addRole(roleFromDb);
            }else{
                try {
                    throw new CustomException("Could not find "+ role +" role");
                } catch (CustomException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        userRepository.save(user);
        return roleRepository.findRoleOfUser(user.getUserId());
    }


}
