package com.example.linkRedirector.service;

import com.example.linkRedirector.exception.BadRequestException;
import com.example.linkRedirector.exception.NotFoundException;
import com.example.linkRedirector.model.Url;
import com.example.linkRedirector.repository.LinkRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class LinkServiceImplTests {

    @Mock
    private LinkRepository linkRepository;
    private LinkService linkService;
    private AutoCloseable closeable;


    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        linkService = new LinkServiceImpl(linkRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testAddUrlThrowsBadRequestException() {
        //given
        String invalidUrl = "https://youtube.com/*";

        //when
        //then
        assertThrows(BadRequestException.class, () -> linkService.addUrl(invalidUrl));
    }

    @Test
    void testAddUrlReturns200() {
        //given
        String validUrl = "https://youtube.com";
        Url dbUrl = new Url();

        //when
        when(linkRepository.findByUrl(validUrl)).thenReturn(dbUrl);
        ResponseEntity<Url> responseEntity = linkService.addUrl(validUrl);

        //then
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    void testAddUrlReturns201() {
        //given
        String validUrl = "https://youtube.com";
        Url dbUrl = new Url();

        //when
        when(linkRepository.findByUrl(validUrl)).thenReturn(null);
        when(linkRepository.save((dbUrl))).thenReturn(dbUrl);
        ResponseEntity<Url> responseEntity = linkService.addUrl(validUrl);

        //then
        assertEquals("201 CREATED", responseEntity.getStatusCode().toString());
    }

    @Test
    void testDeleteUrlThrowsNotFoundException() {
        //given
        String mockCode = "mockCode";

        //when
        when(linkRepository.findByCode(mockCode)).thenReturn(null);

        //then
        assertThrows(NotFoundException.class, () -> linkService.deleteUrl(mockCode));
    }

    @Test
    void testDeleteUrl() {
        //given
        String mockCode = "mockCode";

        //when
        when(linkRepository.findByCode(mockCode)).thenReturn(new Url());

        //then
        assertThat(linkService.deleteUrl(mockCode)).isEqualTo("Deleted");
    }


    @Test
    void testGetUrlThrowsNotFoundException() {
        //given
        String mockCode = "mockCode";

        //when
        when(linkRepository.findByCode(mockCode)).thenReturn(null);

        //then
        assertThrows(NotFoundException.class, () -> linkService.getUrl(mockCode));
    }

    @Test
    void testGetUrl() {
        //given
        String mockCode = "mockCode";

        //when
        when(linkRepository.findByCode(mockCode)).thenReturn(new Url());

        //then
        assertThat(linkService.getUrl(mockCode)).isNotNull();
    }

    @Test
    void testGetAllUrls() {
        //given
        //when
        when(linkRepository.findAll()).thenReturn(new ArrayList<>());

        //then
        assertThat(linkService.getAllUrls()).isNotNull();
    }
}
