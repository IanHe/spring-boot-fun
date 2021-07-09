package com.ian.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ian.domain.Book;
import com.ian.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private BookRepository mockRepository;

    @Before
    public void init() {
        var book = new Book(1L, "someBookName", "Ian", new BigDecimal(99));
        when(mockRepository.findById(1L)).thenReturn(Optional.of(book));
    }

    @Test
    public void find_login_ok() {
        String expected = "{\"id\":1,\"name\":\"someBookName\",\"author\":\"Ian\",\"price\":99}";
        ResponseEntity<String> resp = restTemplate.withBasicAuth("user", "password")
                .getForEntity("/books/1", String.class);
        printJson(resp);
        assertEquals(MediaType.APPLICATION_JSON, resp.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(expected, resp.getBody());
    }

    @Test
    public void find_nologin_401() {
        String expected = "\"status\":401,\"error\":\"Unauthorized\",\"path\":\"/books/1\"";
        ResponseEntity<String> response = restTemplate.getForEntity("/books/1", String.class);
        printJson(response);
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains(expected));
    }

    private static void printJson(Object obj) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}