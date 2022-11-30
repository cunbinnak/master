package com.librarybook.demo.entity;


import lombok.Data;

@Data
public class Book {

    private String title;
    private String author;
    private String bookType;
    private String releaseDate;
    private String numberPage;


}
