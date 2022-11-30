package com.librarybook.demo.repository;

import com.librarybook.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Integer> {
}
