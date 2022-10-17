package com.ccsw.tutorial.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.cliente.model.ClienteDto;
import com.devonfw.module.beanmapping.common.api.BeanMapper;

/**
* @author ccsw
*/
@RequestMapping(value = "/cliente")
@RestController
@CrossOrigin(origins = "*")
public class ClienteController {

  @Autowired
  ClienteService clienteService;

  @Autowired
  BeanMapper beanMapper;

  /**
  * Método para recuperar todas las {@link com.ccsw.tutorial.cliente.model.Cliente}
  * @return
  */
  @RequestMapping(path = "", method = RequestMethod.GET)
  public List<ClienteDto> findAll() {

    return this.beanMapper.mapList(this.clienteService.findAll(), ClienteDto.class);
  }

  /**
  * Método para crear o actualizar una {@link com.ccsw.tutorial.cliente.model.Cliente}
  * @param dto
  * @return
  */
  @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
  public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody ClienteDto dto) {

    this.clienteService.save(id, dto);
  }

  /**
  * Método para borrar una {@link com.ccsw.tutorial.cliente.model.Cliente}
  * @param id
  */
  @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("id") Long id) {

    this.clienteService.delete(id);

  }
}
