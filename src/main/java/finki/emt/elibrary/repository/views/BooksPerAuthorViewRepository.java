package finki.emt.elibrary.repository.views;

import finki.emt.elibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BooksPerAuthorViewRepository extends JpaRepository<Book, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "REFRESH MATERIALIZED VIEW public.books_per_authors", nativeQuery = true)
    void refreshMaterializedView();
}