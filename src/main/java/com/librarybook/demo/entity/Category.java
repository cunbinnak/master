package com.librarybook.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Name;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private Collection<Book> books;
}
