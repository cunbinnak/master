package com.librarybook.demo.repository;

import com.librarybook.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepo extends JpaRepository<Book,Integer> {
    Book findById(int id);
}
