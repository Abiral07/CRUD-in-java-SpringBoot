package com.RESTfullCRUD.BasicCRUD.Controller;

import com.RESTfullCRUD.BasicCRUD.constant.PathConstant;
import com.RESTfullCRUD.BasicCRUD.entity.Role;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;
import com.RESTfullCRUD.BasicCRUD.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class HelloController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public String home(){
        return "Welcome to Home Page!!!!!!!";
    }

    @GetMapping(PathConstant.GET_ROLE)
    public ResponseEntity<List<Role>> getAllRole(){
        return ResponseEntity.ok(roleService.getAllRole());
    }

    @PostMapping(PathConstant.ADD_ROLE)
    public ResponseEntity<Role> addNewRole(@RequestBody Role role) throws CustomException {
        return ResponseEntity.ok(roleService.addNewRole(role));
    }
    @PutMapping(PathConstant.UPDATE_ROLE)
    public ResponseEntity<Role> updateRole(@PathVariable("id") Long id,@RequestBody Role role) throws CustomException {
        return ResponseEntity.ok(roleService.updateRole(id,role));
    }
    @PostMapping(PathConstant.ADD_ROLE_TO_USER)
    public ResponseEntity<Set<String>> addRoleToUser(@PathVariable("id")Long id, @RequestBody Set<String> roles){
        return ResponseEntity.ok(roleService.addRoleToUSer(id,roles));
    }

}
