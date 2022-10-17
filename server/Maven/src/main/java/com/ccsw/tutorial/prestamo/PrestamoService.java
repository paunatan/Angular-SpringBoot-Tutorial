package com.ccsw.tutorial.prestamo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;

public interface PrestamoService {

    /**
     * Método para recuperar un listado paginado de
     * {@link com.ccsw.tutorial.prestamo.model.Prestamo}
     * 
     * @param dto
     * @return listado paginado
     */
    Page<Prestamo> findPage(Long clienteId, Long gameId, Date searchDate, PrestamoSearchDto dto);

    /**
     * fechas de un juego si ya está prestado dentro de unas fechas por un cliente
     * 
     * @param game_id
     * @param prest_date
     * @param devol_date
     * @return Listado
     */
    List<Prestamo> findBorrowedGame(Long game_id, Date prest_date, Date devol_date);

    /**
     * muestra prestamos existentes
     * 
     * @param cliente_id
     * @param prest_date
     * @param devol_date
     * @return Listado
     */
    List<Prestamo> findExistingPrestamos(Long cliente_id, Date prest_date, Date devol_date);

    /**
     * Método para crear o actualizar un
     * {@link com.ccsw.tutorial.prestamo.model.Prestamo}
     * 
     * @param id
     * @param data
     */
    int save(Long id, PrestamoDto data);

    /**
     * Método para borrar un {@link com.ccsw.tutorial.prestamo.model.Prestamo}
     * 
     * @param id Id
     */
    void delete(Long id);

}