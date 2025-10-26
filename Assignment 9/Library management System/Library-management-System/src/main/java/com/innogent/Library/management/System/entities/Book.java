package com.innogent.Library.management.System.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"book_id", "book_name", "book_stock"}) // Exclude members
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long book_id;
    private String book_name;
    private Long book_stock;
    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Author author;
    @ManyToMany(mappedBy = "borrowed_books")
    private Set<Member> members = new HashSet<>();
}
