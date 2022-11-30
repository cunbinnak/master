package com.librarybook.demo.repository;

import com.librarybook.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
    User findByUserNameAndPassword(String userName,String password);
    User findByUserName(String userName);
}
