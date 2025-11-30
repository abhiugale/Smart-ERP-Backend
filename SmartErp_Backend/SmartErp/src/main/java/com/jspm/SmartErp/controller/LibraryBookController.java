// LibraryBookController.java
package com.jspm.SmartErp.controller;

import com.jspm.SmartErp.model.LibraryBook;
import com.jspm.SmartErp.service.LibraryBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/library")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LibraryBookController {

    @Autowired
    private LibraryBookService libraryBookService;

    @GetMapping("/books")
    public ResponseEntity<List<LibraryBook>> getAllBooks() {
        try {
            List<LibraryBook> books = libraryBookService.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Integer id) {
        try {
            return libraryBookService.getBookById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<?> createBook(@Valid @RequestBody LibraryBook book) {
        try {
            LibraryBook createdBook = libraryBookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to create book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Integer id, @Valid @RequestBody LibraryBook bookDetails) {
        try {
            LibraryBook updatedBook = libraryBookService.updateBook(id, bookDetails);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to update book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        try {
            libraryBookService.deleteBook(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<LibraryBook>> searchBooks(@RequestParam String query) {
        try {
            List<LibraryBook> books = libraryBookService.searchBooks(query);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/books/category/{category}")
    public ResponseEntity<List<LibraryBook>> getBooksByCategory(@PathVariable String category) {
        try {
            List<LibraryBook> books = libraryBookService.getBooksByCategory(category);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/books/available")
    public ResponseEntity<List<LibraryBook>> getAvailableBooks() {
        try {
            List<LibraryBook> books = libraryBookService.getAvailableBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/books/issued")
    public ResponseEntity<List<LibraryBook>> getIssuedBooks() {
        try {
            List<LibraryBook> books = libraryBookService.getIssuedBooks();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/books/{id}/issue")
    public ResponseEntity<?> issueBook(@PathVariable Integer id) {
        try {
            LibraryBook issuedBook = libraryBookService.issueBook(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book issued successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to issue book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/books/{id}/return")
    public ResponseEntity<?> returnBook(@PathVariable Integer id) {
        try {
            LibraryBook returnedBook = libraryBookService.returnBook(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book returned successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to return book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/books/rack/{rackLocation}")
    public ResponseEntity<List<LibraryBook>> getBooksByRackLocation(@PathVariable String rackLocation) {
        try {
            List<LibraryBook> books = libraryBookService.getBooksByRackLocation(rackLocation);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}