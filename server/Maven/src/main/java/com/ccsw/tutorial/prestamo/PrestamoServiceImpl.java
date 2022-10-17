package com.ccsw.tutorial.prestamo;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.cliente.ClienteService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;

@Service
@Transactional
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    PrestamoRepository prestamoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    GameService gameService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Prestamo> findPage(Long clienteId, Long gameId, Date searchDate, PrestamoSearchDto dto) {

        return this.prestamoRepository.find(clienteId, gameId, searchDate, dto.getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Prestamo> findBorrowedGame(Long game_id, Date prest_date, Date devol_date) {

        return this.prestamoRepository.findBorrowedGame(game_id, prest_date, devol_date);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Prestamo> findExistingPrestamos(Long cliente_id, Date prest_date, Date devol_date) {

        return this.prestamoRepository.findNumberOfPrestamos(cliente_id, prest_date, devol_date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int save(Long id, PrestamoDto data) {

        Prestamo prestamo = null;

        if (gameBorrowed(data) == true) {
            return 100;
        } else if (clienteWithPrestamos(data) == true) {
            return 200;
        } else {
            prestamo = new Prestamo();

            BeanUtils.copyProperties(data, prestamo, "id", "cliente", "game");

            prestamo.setCliente(clienteService.get(data.getCliente().getId()));
            prestamo.setGame(gameService.get(data.getGame().getId()));

            this.prestamoRepository.save(prestamo);

            return 0;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {

        this.prestamoRepository.deleteById(id);
    }

    /**
     * Comprueba si el juego ya ha sido prestado.
     * 
     * @param dto
     * 
     * @return Boolean.
     */
    private boolean gameBorrowed(PrestamoDto dto) {

        boolean result = false;

        List<Prestamo> prestamos = (List<Prestamo>) this.findBorrowedGame(dto.getGame().getId(), dto.getPrest_date(),
                dto.getDevol_date());

        if (prestamos.size() == 0)
            result = false;
        else
            result = true;

        return result;
    }

    /**
     * Comprueba si un cliente tiene dos o más préstamos en una misma fecha.
     * 
     * @param dto .
     * 
     * @return Boolean.
     */
    private boolean clienteWithPrestamos(PrestamoDto dto) {

        boolean result = false;

        List<Prestamo> prestamos = (List<Prestamo>) this.findExistingPrestamos(dto.getCliente().getId(),
                dto.getPrest_date(), dto.getDevol_date());

        if (prestamos.size() == 2)
            result = true;
        else
            result = false;

        return result;
    }
}