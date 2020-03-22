package br.com.vipofertas.service;

import br.com.vipofertas.model.Parceiro;

import java.util.List;

public interface ParceiroService {

    Parceiro salvar(Parceiro parceiro);
    List<Parceiro> findParceiroByCidade(Long idCidade);
}
