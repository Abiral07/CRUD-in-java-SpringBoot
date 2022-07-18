package com.RESTfullCRUD.BasicCRUD.repository;

import com.RESTfullCRUD.BasicCRUD.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
    Role findDistinctByRoleId(Long id);

    @Query(value = "SELECT r.role_name FROM crud.role as r  right join crud.user_role on role_id = role_role_id where user_user_id = :userId",
    nativeQuery = true)
    Set<String> findRoleOfUser(Long userId);
}
