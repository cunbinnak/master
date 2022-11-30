package com.librarybook.demo.repository;

import com.librarybook.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepo extends JpaRepository<Book,Integer> {
    @Query(value = "select * from book b inner join category c on b.category_id=c.id",nativeQuery = true)
    Book findByIdBook(int id);
}
