package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Cidade;
import br.com.vipofertas.repository.CidadeRepository;
import br.com.vipofertas.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeServiceImpl implements CidadeService{

    @Autowired
    private CidadeRepository cidadeRepository;

    @Override
    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }
}
