package com.ccsw.tutorial.cliente;

import java.util.List;

import com.ccsw.tutorial.cliente.model.Cliente;
import com.ccsw.tutorial.cliente.model.ClienteDto;

/**
 * @author ccsw
 *
 */
public interface ClienteService {

    Cliente get(Long id);

    /**
     * Método para recuperar todas las
     * {@link com.ccsw.tutorial.cliente.model.Cliente}
     * 
     * @return
     */
    List<Cliente> findAll();

    /**
     * Método para crear o actualizar una
     * {@link com.ccsw.tutorial.cliente.model.Cliente}
     * 
     * @param dto
     * @return
     */
    void save(Long id, ClienteDto dto);

    /**
     * Método para borrar una {@link com.ccsw.tutorial.cliente.model.Cliente}
     * 
     * @param id
     */
    void delete(Long id);
}
