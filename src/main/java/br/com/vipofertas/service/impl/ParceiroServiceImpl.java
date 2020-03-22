package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Cidade;
import br.com.vipofertas.model.Parceiro;
import br.com.vipofertas.repository.ParceiroRepository;
import br.com.vipofertas.service.ParceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParceiroServiceImpl implements ParceiroService{

    @Autowired
    private ParceiroRepository parceiroRepository;

    @Override
    public Parceiro salvar(Parceiro parceiro) {
        return parceiroRepository.save(parceiro);
    }

    @Override
    public List<Parceiro> findParceiroByCidade(Long idCidade) {
        Cidade cidade = new Cidade();
        cidade.setId(idCidade);
        return parceiroRepository.findParceiroByCidade(cidade);
    }
}
