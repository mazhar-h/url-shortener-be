package com.example.linkRedirector.controller;

import com.example.linkRedirector.model.Url;
import com.example.linkRedirector.service.LinkService;
import com.example.linkRedirector.service.LinkServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class LinkController {

    LinkService linkService;

    public LinkController(LinkServiceImpl linkService) {
        this.linkService = linkService;
    }

    @PostMapping("urls")
    public ResponseEntity<Url> addNewUrl(@RequestBody Url newUrl){
        String urlValue = newUrl.getUrl();
        return linkService.addUrl(urlValue);
    }

    @GetMapping("urls/{code}")
    public Url getUrl(@PathVariable("code") String code){
        return linkService.getUrl(code);
    }

    @GetMapping("urls")
    public List<Url> getAllUrls(){
        return linkService.getAllUrls();
    }

    @DeleteMapping("urls/{code}")
    public String deleteUrl(@PathVariable("code") String code) {
        return linkService.deleteUrl(code);
    }
}
