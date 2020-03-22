package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Unidade;
import br.com.vipofertas.repository.UnidadeRepository;
import br.com.vipofertas.service.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeServiceImpl implements UnidadeService{

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Override
    public List<String> findDescricao() {
        return unidadeRepository.findDescricao();
    }

    @Override
    public List<String> findDescricaoUnidade(String descricao) {
        return unidadeRepository.findDescricaoUnidade(descricao);
    }
}
