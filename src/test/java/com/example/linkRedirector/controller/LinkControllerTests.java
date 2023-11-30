package com.example.linkRedirector.controller;

import com.example.linkRedirector.model.Url;
import com.example.linkRedirector.service.LinkService;
import com.example.linkRedirector.service.LinkServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(LinkController.class)
public class LinkControllerTests {
    @Autowired
    MockMvc mockMvc;
    @MockBean(classes = LinkServiceImpl.class)
    LinkService linkService;

    @Test
    void testAddNewUrl() throws Exception {
        String uri = "/api/v1/urls";
        String mockUrl = "https://mockurl.com";
        Url url = new Url();
        url.setUrl(mockUrl);
        url.setCode("mockCode");
        String inputJson = mapToJson(url);
        when(linkService.addUrl(mockUrl)).thenReturn(ResponseEntity.of(Optional.of(url)));
        MvcResult mvcResult = this.mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void testGetUrl() throws Exception {
        String uri = "/api/v1/urls/mockCode";
        String mockUrl = "https://mockurl.com";
        Url url = new Url();
        url.setUrl(mockUrl);
        url.setCode("mockCode");

        when(linkService.getUrl("mockCode")).thenReturn(url);
        MvcResult mvcResult = this.mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void testGetAllUrls() throws Exception {
        String uri = "/api/v1/urls";
        String mockUrl = "https://mockurl.com";
        Url url = new Url();
        url.setUrl(mockUrl);
        url.setCode("mockCode");

        when(linkService.getAllUrls()).thenReturn(List.of(url));
        MvcResult mvcResult = this.mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void testDeleteUrl() throws Exception {
        String uri = "/api/v1/urls/mockCode";
        String mockUrl = "https://mockurl.com";
        Url url = new Url();
        url.setUrl(mockUrl);
        url.setCode("mockCode");

        when(linkService.deleteUrl("mockCode")).thenReturn("Deleted");
        MvcResult mvcResult = this.mockMvc.perform(delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("Deleted", response);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
