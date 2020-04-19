package com.example.bookservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.example.bookservice.domain.Book;
import com.example.bookservice.persistence.*;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long bookId) {
        return Optional.ofNullable(bookRepository.getOne(bookId))
            .orElseThrow(() -> new BookNotFoundException("Book not found. ID: " + bookId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book createBook(Book book) {
        final Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        return bookRepository.save(newBook);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book updateBook(Map<String, String> updates, Long bookId) {
        final Book book = findBookById(bookId);
        updates.keySet()
            .forEach(key -> {
                switch (key) {
                case "author":
                    book.setAuthor(updates.get(key));
                    break;
                case "title":
                    book.setTitle(updates.get(key));
                }
            });
        return bookRepository.save(book);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book updateBook(Book book, Long bookId) {
        
        Assert.notNull(book, "book must not be null");
        Assert.notNull(bookRepository.getOne(bookId),"book must not be null");
        return bookRepository.save(book);
    }
}
