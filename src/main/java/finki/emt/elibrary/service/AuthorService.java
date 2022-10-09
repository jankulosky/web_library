package finki.emt.elibrary.service;

import finki.emt.elibrary.model.Author;
import finki.emt.elibrary.model.Country;
import finki.emt.elibrary.model.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> findById(Long id);

    List<Author> findAll();

    Optional<Author> save(AuthorDto request);

    void deleteById(Long id);
}