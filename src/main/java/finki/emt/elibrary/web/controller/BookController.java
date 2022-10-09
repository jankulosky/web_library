package finki.emt.elibrary.web.controller;

import finki.emt.elibrary.model.Author;
import finki.emt.elibrary.model.Book;
import finki.emt.elibrary.model.enumerations.Category;
import finki.emt.elibrary.service.AuthorService;
import finki.emt.elibrary.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("bodyContent", "books");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/edit-form/{id}")
    public String editBookPage(@PathVariable Long id, Model model) {
        if (this.bookService.findById(id).isPresent()) {
            Book book = this.bookService.findById(id).get();
            List<Author> authors = this.authorService.findAll();
            List<Category> categories = List.of(Category.values());
            model.addAttribute("authors", authors);
            model.addAttribute("categories", categories);
            model.addAttribute("book", book);
            model.addAttribute("bodyContent", "add-product");
            return "master-template";
        }
        return "redirect:/books?error=ProductNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBookPage(Model model) {
        List<Author> manufacturers = this.authorService.findAll();
        List<Category> categories = List.of(Category.values());
        model.addAttribute("authors", manufacturers);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-book");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveBook(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Integer availableCopies,
            @RequestParam Category category,
            @RequestParam Long Author) {
        if (id != null) {
            this.bookService.edit(id, name, category, Author, availableCopies);
        } else {
            this.bookService.add(name, category, Author, availableCopies);
        }
        return "redirect:/books";
    }
}