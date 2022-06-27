package com.RESTfullCRUD.BasicCRUD.repository;

import com.RESTfullCRUD.BasicCRUD.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
