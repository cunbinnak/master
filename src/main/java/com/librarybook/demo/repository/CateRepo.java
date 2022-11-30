package com.librarybook.demo.repository;

import com.librarybook.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CateRepo extends JpaRepository<Category, Integer> {

    List<Category> findAll();
}
