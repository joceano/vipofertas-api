package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Estado;
import br.com.vipofertas.repository.EstadoRepository;
import br.com.vipofertas.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoServiceImpl implements EstadoService{

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }
}
