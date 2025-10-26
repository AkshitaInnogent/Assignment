package com.innogent.Library.management.System.repositories;

import com.innogent.Library.management.System.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author,Long> {
}
