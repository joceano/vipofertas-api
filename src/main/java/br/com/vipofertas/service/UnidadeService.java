package br.com.vipofertas.service;

import br.com.vipofertas.model.Unidade;

import java.util.List;

public interface UnidadeService {
    List<String> findDescricaoUnidade(String descricao);
    List<String> findDescricao();
}
