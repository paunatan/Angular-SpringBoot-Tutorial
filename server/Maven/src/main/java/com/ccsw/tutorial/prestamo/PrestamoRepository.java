package com.ccsw.tutorial.prestamo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ccsw.tutorial.prestamo.model.Prestamo;

public interface PrestamoRepository extends CrudRepository<Prestamo, Long> {

    /**
     * Método para recuperar un listado paginado de
     * {@link com.ccsw.tutorial.prestamo.model.Prestamo}
     * 
     * @param pageable
     * @return
     */
    @Query("select l from Prestamo l where (:cliente is null or l.cliente.id = :cliente) and (:game is null or l.game.id = :game) and (:date is null or :date between l.prest_date and l.devol_date)")
    Page<Prestamo> find(@Param("cliente") Long cliente, @Param("game") Long game, @Param("date") Date date,
            Pageable pageable);

    /**
     * Método para recuperar un listado filtado por juego y fechas
     * 
     * @param game
     * @param prest_date
     * @param devol_date
     * 
     * @return si un juego está prestado.
     */
    @Query("select l from Prestamo l where (l.game.id = :game) and ((l.prest_date between :prest_date and :devol_date) or (l.devol_date between :prest_date and :devol_date) or (:prest_date between l.prest_date and l.devol_date) or (:devol_date between l.prest_date and l.devol_date))")
    List<Prestamo> findBorrowedGame(@Param("game") Long game, @Param("prest_date") Date prest_date,
            @Param("devol_date") Date devol_date);

    /**
     * Método para recuperar un listado filtrado por cliente, fecha de inicio y fin.
     *
     * @param cliente
     * @param prest_date
     * @param devol_date
     * 
     * @return Prestamo pendiente entre fechas.
     */
    @Query("select l from Prestamo l where (l.cliente.id = :cliente) and ((l.prest_date between :prest_date and :devol_date) or (l.devol_date between :prest_date and :devol_date) or (:prest_date between l.prest_date and l.devol_date) or (:devol_date between l.prest_date and l.devol_date))")
    List<Prestamo> findNumberOfPrestamos(@Param("cliente") Long cliente, @Param("prest_date") Date prest_date,
            @Param("devol_date") Date devol_date);

}