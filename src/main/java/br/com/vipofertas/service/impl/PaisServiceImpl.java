package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Pais;
import br.com.vipofertas.repository.PaisRepository;
import br.com.vipofertas.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisServiceImpl implements PaisService{

    @Autowired
    private PaisRepository paisRepository;

    @Override
    public Pais salvar(Pais pais) {
        return paisRepository.save(pais);
    }
}
