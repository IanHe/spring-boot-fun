package com.ian.controller;

import com.ian.domain.Book;
import com.ian.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository mockBookRepository;

    @Before
    public void init() {
        var book = new Book(1L, "someBookName", "Ian", new BigDecimal(99));
        when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));
    }

    @Test
    public void find_book_by_id_OK() throws Exception {
        mockMvc.perform(get("/books/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("someBookName")))
                .andExpect(jsonPath("$.author", is("Ian")))
                .andExpect(jsonPath("$.price", is(99)));
    }

    @Test
    public void find_all_books_OK() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1L, "Book A", "Ian", BigDecimal.valueOf(1.99)),
                new Book(2L, "Book B", "Ian", BigDecimal.valueOf(2.99)));
        when(mockBookRepository.findAll()).thenReturn(books);
        mockMvc.perform(get("/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Book A")))
                .andExpect(jsonPath("$[0].author", is("Ian")))
                .andExpect(jsonPath("$[0].price", is(1.99)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Book B")))
                .andExpect(jsonPath("$[1].author", is("Ian")))
                .andExpect(jsonPath("$[1].price", is(2.99)));
    }
}