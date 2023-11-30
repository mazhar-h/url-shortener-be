package com.example.linkRedirector.service;

import com.example.linkRedirector.exception.BadRequestException;
import com.example.linkRedirector.exception.NotFoundException;
import com.example.linkRedirector.model.Url;
import com.example.linkRedirector.repository.LinkRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Random;

@Service
public class LinkServiceImpl implements LinkService {

    LinkRepository linkRepository;

    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public ResponseEntity<Url> addUrl(String url) {
        if (!isUrlValid(url))
            throw new BadRequestException();

        Url dbUrl = linkRepository.findByUrl(url);

        if (dbUrl != null)
            return new ResponseEntity<>(dbUrl, HttpStatus.OK);

        Url newUrl = new Url();
        newUrl.setUrl(url);
        newUrl.setCode(getCode(5));
        newUrl = linkRepository.save(newUrl);
        return new ResponseEntity<>(newUrl, HttpStatus.CREATED);
    }

    @Override
    public String deleteUrl(String code) {
        Url dbUrl = linkRepository.findByCode(code);
        if (dbUrl == null)
            throw new NotFoundException();
        linkRepository.deleteByCode(code);
        return "Deleted";
    }

    @Override
    public Url getUrl(String code) {
        Url dbUrl = linkRepository.findByCode(code);
        if (dbUrl == null)
            throw new NotFoundException();
        return dbUrl;
    }

    @Override
    public List<Url> getAllUrls() {
        return linkRepository.findAll();
    }

    private boolean isUrlValid(String url)
    {
        if (url.matches("^(https?:\\/\\/)?" + // validate protocol
                "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|" + // validate domain name
                "((\\d{1,3}\\.){3}\\d{1,3}))" + // validate OR ip (v4) address
                "(\\:\\d+)?(\\/[-a-z\\d%_.~+@]*)*" + // validate port and path
                "(\\?[;&a-z\\d%_.~+=-]*)?" + // validate query string
                "(\\#[-a-z\\d_]*)?$")) // validate fragment locator
        {
            try {
                new URL(url).toURI();
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        else
            return false;
    }
    private String getCode(int length){
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            switch (rand.nextInt(3)) {
                case 0 -> code.append((char) (rand.nextInt(26) + 'a'));
                case 1 -> code.append((char) (rand.nextInt(26) + 'A'));
                case 2 -> code.append(rand.nextInt(10));
            }
        }
        return code.toString();
    }
}
