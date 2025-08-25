package com.jspm.SmartErp.repository;
import com.jspm.SmartErp.model.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Integer> {
    List<LibraryBook> findByAvailableTrue();
    List<LibraryBook> findByCategory(String category);
    List<LibraryBook> findByAuthor(String author);
}
