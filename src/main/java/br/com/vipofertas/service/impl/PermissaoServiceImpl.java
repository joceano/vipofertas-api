package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Permissao;
import br.com.vipofertas.repository.PermissaoRepository;
import br.com.vipofertas.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoServiceImpl implements PermissaoService{

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Override
    public Permissao salvar(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }
}
