package com.ccsw.tutorial.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.cliente.model.Cliente;
import com.ccsw.tutorial.cliente.model.ClienteDto;

/**
 * @author ccsw
 *
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Cliente get(Long id) {

        return this.clienteRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cliente> findAll() {

        return (List<Cliente>) this.clienteRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClienteDto dto) {

        Cliente categoria = null;

        if (id == null)
            categoria = new Cliente();
        else
            categoria = this.clienteRepository.findById(id).orElse(null);

        categoria.setName(dto.getName());

        this.clienteRepository.save(categoria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {

        this.clienteRepository.deleteById(id);

    }
}
