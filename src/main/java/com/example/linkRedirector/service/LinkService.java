package com.example.linkRedirector.service;

import com.example.linkRedirector.model.Url;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LinkService {
    ResponseEntity<Url> addUrl(String url);
    String deleteUrl(String code);
    Url getUrl(String code);
    List<Url> getAllUrls();
}
