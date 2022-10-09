package finki.emt.elibrary.model.dto;

import finki.emt.elibrary.model.enumerations.Category;
import lombok.Data;

@Data
public class BookDto {

    private Long id;

    private String name;

    private Category category;

    private Long author;

    private Integer availableCopies;

    public BookDto(){
    }

    public BookDto(Long id, String name, Category category, Long author, Integer availableCopies) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
    }
}