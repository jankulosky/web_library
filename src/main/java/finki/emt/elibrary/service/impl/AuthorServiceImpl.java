package finki.emt.elibrary.service.impl;

import finki.emt.elibrary.model.Author;
import finki.emt.elibrary.model.Country;
import finki.emt.elibrary.model.dto.AuthorDto;
import finki.emt.elibrary.repository.AuthorRepository;
import finki.emt.elibrary.repository.CountryRepository;
import finki.emt.elibrary.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryRepository countryRepository) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Optional<Author> save(AuthorDto request) {
        Optional<Country> country = this.countryRepository.findById(request.country);

        return Optional.of(this.authorRepository.save(new Author(request.name, request.surname, country.orElse(null))));
    }

    @Override
    public void deleteById(Long id) {
        this.authorRepository.deleteById(id);
    }
}
