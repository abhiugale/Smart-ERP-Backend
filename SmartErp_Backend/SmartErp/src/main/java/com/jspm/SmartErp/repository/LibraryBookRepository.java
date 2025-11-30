// LibraryBookRepository.java
package com.jspm.SmartErp.repository;

import com.jspm.SmartErp.model.LibraryBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryBookRepository extends JpaRepository<LibraryBook, Integer> {

    // Find books by title (case insensitive search)
    List<LibraryBook> findByTitleContainingIgnoreCase(String title);

    // Find books by author (case insensitive search)
    List<LibraryBook> findByAuthorContainingIgnoreCase(String author);

    // Find books by category
    List<LibraryBook> findByCategoryContainingIgnoreCase(String category);

    // Find book by ISBN
    Optional<LibraryBook> findByIsbn(String isbn);

    // Find available books
    List<LibraryBook> findByAvailableTrue();

    // Find issued books
    List<LibraryBook> findByAvailableFalse();

    // Search books by title or author
    @Query("SELECT b FROM LibraryBook b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<LibraryBook> searchByTitleOrAuthor(@Param("query") String query);

    // Check if ISBN already exists (for create)
    boolean existsByIsbn(String isbn);

    // Check if ISBN exists for other books (for update)
    @Query("SELECT COUNT(b) > 0 FROM LibraryBook b WHERE b.isbn = :isbn AND b.bookId != :bookId")
    boolean existsByIsbnAndBookIdNot(@Param("isbn") String isbn, @Param("bookId") Integer bookId);

    // Find books by rack location
    List<LibraryBook> findByRackLocationContainingIgnoreCase(String rackLocation);
}