create view books_per_authors as
(
select author_id, count(b.id) as num_book
from book b
         join authors a
              on a.id = b.author_id
group by author_id
)