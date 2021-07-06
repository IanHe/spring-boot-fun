package com.ian.repository;

import com.ian.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void test_crud_on_book() {
        var book = new Book("someBook", "someAuthor", BigDecimal.valueOf(99));
        var savedBook = bookRepository.save(book);
        var findBook = bookRepository.findById(savedBook.getId());
        assertThat(findBook).isPresent();
        assertThat(findBook.get()).isEqualTo(savedBook);

        savedBook.setAuthor("newAuthor");
        savedBook = bookRepository.save(savedBook);
        findBook = bookRepository.findById(savedBook.getId());
        assertThat(findBook).isPresent();
        assertThat(findBook.get().getAuthor()).isEqualTo("newAuthor");

        bookRepository.deleteById(savedBook.getId());
        assertThat(bookRepository.findAll()).isEmpty();
    }
}