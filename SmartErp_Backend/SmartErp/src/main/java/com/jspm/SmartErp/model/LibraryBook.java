// LibraryBook.java
package com.jspm.SmartErp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "library_books")
public class LibraryBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author is required")
    @Column(nullable = false)
    private String author;

    @NotBlank(message = "ISBN is required")
    @Column(unique = true, nullable = false)
    private String isbn;

    @NotBlank(message = "Category is required")
    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Boolean available = true;

    private String rackLocation;

    // Constructors
    public LibraryBook() {
        this.available = true;
    }

    public LibraryBook(String title, String author, String isbn, String category, String rackLocation) {
        this();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.rackLocation = rackLocation;
    }

    // Getters and Setters
    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public String getRackLocation() { return rackLocation; }
    public void setRackLocation(String rackLocation) { this.rackLocation = rackLocation; }

    @Override
    public String toString() {
        return "LibraryBook{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category='" + category + '\'' +
                ", available=" + available +
                ", rackLocation='" + rackLocation + '\'' +
                '}';
    }
}