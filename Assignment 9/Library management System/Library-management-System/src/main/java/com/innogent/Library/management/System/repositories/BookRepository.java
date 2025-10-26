package com.innogent.Library.management.System.repositories;

import com.innogent.Library.management.System.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {

}
