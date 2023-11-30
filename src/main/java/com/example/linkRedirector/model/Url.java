package com.example.linkRedirector.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Url {

    private String url;
    @Id
    private String code;
    @CreationTimestamp
    private LocalDateTime dateCreated;
}
