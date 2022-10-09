package finki.emt.elibrary.service.impl;

import finki.emt.elibrary.model.Author;
import finki.emt.elibrary.model.Book;
import finki.emt.elibrary.model.dto.BookDto;
import finki.emt.elibrary.model.enumerations.Category;
import finki.emt.elibrary.model.events.BookCreatedEvent;
import finki.emt.elibrary.model.exceptions.AuthorNotFoundException;
import finki.emt.elibrary.model.exceptions.BookNotFoundException;
import finki.emt.elibrary.repository.AuthorRepository;
import finki.emt.elibrary.repository.BookRepository;
import finki.emt.elibrary.repository.views.BooksPerAuthorViewRepository;
import finki.emt.elibrary.service.BookService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository BookRepository;
    private final AuthorRepository AuthorRepository;
    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public BookServiceImpl(finki.emt.elibrary.repository.BookRepository bookRepository,
                           finki.emt.elibrary.repository.AuthorRepository authorRepository,
                           BooksPerAuthorViewRepository booksPerAuthorViewRepository,
                           ApplicationEventPublisher applicationEventPublisher) {
        BookRepository = bookRepository;
        AuthorRepository = authorRepository;
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Book> findAll() {
        return this.BookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.BookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByName(String name) {
        return this.BookRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Book> add(String name, Category category, Long authorId, Integer availableCopies) {
        Author author = this.AuthorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        this.BookRepository.deleteByName(name);
        Book book = new Book(name, category, author, availableCopies);
        this.BookRepository.save(book);

        this.applicationEventPublisher.publishEvent(new BookCreatedEvent(book));
        return Optional.of(book);
    }

    @Override
    public Optional<Book> add(BookDto bookDto) {
        Author author = this.AuthorRepository.findById(bookDto.getAuthor())
                .orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthor()));

        this.BookRepository.deleteByName(bookDto.getName());
        Book book = new Book(bookDto.getName(), bookDto.getCategory(), author, bookDto.getAvailableCopies());
        this.BookRepository.save(book);

        this.applicationEventPublisher.publishEvent(new BookCreatedEvent(book));
        return Optional.of(book);
    }

    @Override
    @Transactional
    public Optional<Book> edit(Long id, String name, Category category, Long authorId, Integer availableCopies) {

        Book book = this.BookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setName(name);
        book.setAvailableCopies(availableCopies);
        book.setCategory(category);

        Author author = this.AuthorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));
        book.setAuthor(author);

        this.BookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, BookDto bookDto) {
        Book book = this.BookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setName(bookDto.getName());
        book.setAvailableCopies(bookDto.getAvailableCopies());
        book.setCategory(bookDto.getCategory());

        Author author = this.AuthorRepository.findById(bookDto.getAuthor())
                .orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthor()));
        book.setAuthor(author);

        this.BookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public void deleteById(Long id) {
        this.BookRepository.deleteById(id);
    }

    @Override
    public void refreshMaterializedView() {
        this.booksPerAuthorViewRepository.refreshMaterializedView();
    }
}
