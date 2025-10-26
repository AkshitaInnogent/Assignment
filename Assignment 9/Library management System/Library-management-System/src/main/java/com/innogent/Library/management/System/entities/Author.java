package com.innogent.Library.management.System.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

    @Entity
    @Table(name = "authors")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Author {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long auth_id;
        private String auth_name;
        private String auth_email;
        @OneToMany(mappedBy = "author")
        private List<Book> books = new ArrayList<>();
    }

