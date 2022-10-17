package com.ccsw.tutorial.prestamo;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.config.mapper.BeanMapper;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;

@RequestMapping(value = "/prestamo")
@RestController
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    PrestamoService prestamoService;

    @Autowired
    BeanMapper beanMapper;

    /**
     * Método para recuperar un listado paginado de
     * {@link com.ccsw.tutorial.prestamo.model.Prestamo}
     * 
     * @param dto
     * @return Listado paginado.
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<PrestamoDto> findPage(@RequestParam(value = "clienteId", required = false) Long clienteId,
            @RequestParam(value = "gameId", required = false) Long gameId,
            @RequestParam(value = "searchDate", required = false) Date searchDate, @RequestBody PrestamoSearchDto dto) {

        return this.beanMapper.mapPage(this.prestamoService.findPage(clienteId, gameId, searchDate, dto),
                PrestamoDto.class);
    }

    /**
     * Método para crear o actualizar un
     * {@link com.ccsw.tutorial.prestamo.model.Prestamo}
     * 
     * @param id
     * @param data
     * @return int.
     */
    @ResponseBody
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public int save(@PathVariable(name = "id", required = false) Long id, @RequestBody PrestamoDto data) {

        return this.prestamoService.save(id, data);
    }

    /**
     * Método para borrar un {@link com.ccsw.tutorial.prestamo.model.Prestamo}
     * 
     * @param id
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {

        this.prestamoService.delete(id);

    }

}