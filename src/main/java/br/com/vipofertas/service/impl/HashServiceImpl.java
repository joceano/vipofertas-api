package br.com.vipofertas.service.impl;

import br.com.vipofertas.service.HashService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashServiceImpl implements HashService {

    /**
     * Retorna um hash com formato MD5.
     **/
    @Override
    public String gerar(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            return String.valueOf(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
