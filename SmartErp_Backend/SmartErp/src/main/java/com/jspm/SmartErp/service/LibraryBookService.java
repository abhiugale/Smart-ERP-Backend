// LibraryBookService.java
package com.jspm.SmartErp.service;

import com.jspm.SmartErp.model.LibraryBook;
import com.jspm.SmartErp.repository.LibraryBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryBookService {

    @Autowired
    private LibraryBookRepository libraryBookRepository;

    public List<LibraryBook> getAllBooks() {
        return libraryBookRepository.findAll();
    }

    public Optional<LibraryBook> getBookById(Integer id) {
        return libraryBookRepository.findById(id);
    }

    public LibraryBook createBook(LibraryBook book) {
        // Check if ISBN already exists
        if (libraryBookRepository.existsByIsbn(book.getIsbn())) {
            throw new RuntimeException("Book with ISBN '" + book.getIsbn() + "' already exists");
        }
        return libraryBookRepository.save(book);
    }

    public LibraryBook updateBook(Integer id, LibraryBook bookDetails) {
        LibraryBook book = libraryBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Check if ISBN is being changed and if it conflicts with another book
        if (!book.getIsbn().equals(bookDetails.getIsbn())) {
            if (libraryBookRepository.existsByIsbnAndBookIdNot(bookDetails.getIsbn(), id)) {
                throw new RuntimeException("Book with ISBN '" + bookDetails.getIsbn() + "' already exists");
            }
        }

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setCategory(bookDetails.getCategory());
        book.setAvailable(bookDetails.getAvailable());
        book.setRackLocation(bookDetails.getRackLocation());

        return libraryBookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        LibraryBook book = libraryBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        libraryBookRepository.delete(book);
    }

    public List<LibraryBook> searchBooks(String query) {
        return libraryBookRepository.searchByTitleOrAuthor(query);
    }

    public List<LibraryBook> getBooksByCategory(String category) {
        return libraryBookRepository.findByCategoryContainingIgnoreCase(category);
    }

    public List<LibraryBook> getAvailableBooks() {
        return libraryBookRepository.findByAvailableTrue();
    }

    public List<LibraryBook> getIssuedBooks() {
        return libraryBookRepository.findByAvailableFalse();
    }

    public LibraryBook issueBook(Integer id) {
        LibraryBook book = libraryBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        if (!book.getAvailable()) {
            throw new RuntimeException("Book is already issued");
        }

        book.setAvailable(false);
        return libraryBookRepository.save(book);
    }

    public LibraryBook returnBook(Integer id) {
        LibraryBook book = libraryBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        if (book.getAvailable()) {
            throw new RuntimeException("Book is already available");
        }

        book.setAvailable(true);
        return libraryBookRepository.save(book);
    }

    public List<LibraryBook> getBooksByRackLocation(String rackLocation) {
        return libraryBookRepository.findByRackLocationContainingIgnoreCase(rackLocation);
    }
}