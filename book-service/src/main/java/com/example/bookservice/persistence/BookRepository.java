package com.example.bookservice.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookservice.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
}
