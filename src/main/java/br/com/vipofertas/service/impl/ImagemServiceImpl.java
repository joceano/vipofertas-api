package br.com.vipofertas.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.vipofertas.model.Oferta;
import br.com.vipofertas.service.HashService;
import br.com.vipofertas.service.OfertaService;
import br.com.vipofertas.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @autor -  Joceano Alves de Borba - <alves.joceano@gmail.com>
 * Service: ImagemServiceImpl, regras de negócios da imagem das ofertas.
 * data: 25/02/2018
 **/
@Service
public class ImagemServiceImpl implements ImagemService {

    @Autowired
    OfertaService ofertaService;

    @Autowired
    HashService hashService;

    @Value("${imagem.diretorio}")
    private String dir;

    private Path rootLocation;

    /**
     * Salva a imagem no diretório definido no arquivo de propriedades
     **/
    @Override
    public ResponseEntity<?> store(Long id, MultipartFile file) {
        try {
            rootLocation = Paths.get(dir);
            String[] fileFrags = file.getOriginalFilename().split("\\.");
            String extension = fileFrags[fileFrags.length-1];
            String nomeImagem = hashService.gerar(file.getOriginalFilename()) + "." + extension;
            Files.copy(file.getInputStream(),
                    this.rootLocation.resolve(nomeImagem));
            Oferta oferta = ofertaService.findOne(id);
            oferta.setImagem(nomeImagem);
            return ResponseEntity.status(HttpStatus.OK).body(ofertaService.salvar(oferta));
        } catch (Exception e) {
            throw new RuntimeException("FALHA NO UPLOAD DA IMAGEM!");
        }
    }

    /**
     * Retorna a imagem pelo nome.
     **/
    @Override
    public Resource loadFile(String filename) {
        try {
            rootLocation = Paths.get(dir);
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    /**
     * Deleta a imagem do diretório
     **/
    @Override
    public void deleteAll() {
        rootLocation = Paths.get(dir);
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            rootLocation = Paths.get(dir);
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}