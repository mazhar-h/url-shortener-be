package com.example.linkRedirector.repository;

import com.example.linkRedirector.model.Url;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Url, String> {

    Url findByUrl(String url);
    Url findByCode(String code);
    @Transactional
    void deleteByCode(String code);
}
