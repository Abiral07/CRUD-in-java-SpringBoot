package com.RESTfullCRUD.BasicCRUD.repository;

import com.RESTfullCRUD.BasicCRUD.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUserName(String name);

    @Query("Select u From User u, Address a where u.address = a And a.country=?1")
    List<User> getByCountry(String country);

    @Query(value = "Select * from user where user_name = :userName limit 1",
            nativeQuery = true)
    User findMatchedUser(String userName);
}