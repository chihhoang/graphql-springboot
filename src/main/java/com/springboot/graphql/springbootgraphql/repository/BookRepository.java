package com.springboot.graphql.springbootgraphql.repository;

import com.springboot.graphql.springbootgraphql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
