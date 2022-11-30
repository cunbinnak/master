package com.librarybook.demo.repository;

import com.librarybook.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,String> {

    User findUserByNameAndPassword(String userName,String password);
    User findByName(String userName);
}
