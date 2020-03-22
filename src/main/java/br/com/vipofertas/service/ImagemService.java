package br.com.vipofertas.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

public interface ImagemService {
    ResponseEntity<?> store(Long id, MultipartFile file);
    Resource loadFile(String filename);
    void deleteAll();
    void init();
}
