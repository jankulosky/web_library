package finki.emt.elibrary.service;

import finki.emt.elibrary.model.Book;
import finki.emt.elibrary.model.dto.BookDto;
import finki.emt.elibrary.model.enumerations.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findByName(String name);

    Optional<Book> add(String name, Category category, Long Author, Integer availableCopies);

    Optional<Book> add(BookDto bookDto);

    Optional<Book> edit(Long id, String name, Category category, Long Author, Integer availableCopies);

    Optional<Book> edit(Long id, BookDto bookDto);

    void deleteById(Long id);

    void refreshMaterializedView();

}