package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Usuario;
import br.com.vipofertas.repository.UsuarioRepository;
import br.com.vipofertas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
