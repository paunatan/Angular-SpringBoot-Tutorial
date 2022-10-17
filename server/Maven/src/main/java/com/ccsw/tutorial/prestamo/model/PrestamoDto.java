package com.ccsw.tutorial.prestamo.model;

import java.sql.Date;

import com.ccsw.tutorial.cliente.model.ClienteDto;
import com.ccsw.tutorial.game.model.GameDto;

public class PrestamoDto {

    private Long id;

    private GameDto game;

    private ClienteDto cliente;

    private Date prest_date;
    private Date devol_date;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id new value of {@link #getId}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return game
     */
    public GameDto getGame() {
        return game;
    }

    /**
     * @param game new value of {@link #getGame}
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     * @return cliente
     */
    public ClienteDto getCliente() {
        return cliente;
    }

    /**
     * @param cliente new value of {@link #getCliente}
     */
    public void setCliente(ClienteDto cliente) {
        this.cliente = cliente;
    }

    /**
     * @return prest_date
     */
    public Date getPrest_date() {
        return prest_date;
    }

    /**
     * @param prest_date new value of {@link #getPrest_date}
     */
    public void setPrest_date(Date prest_date) {
        this.prest_date = prest_date;
    }

    /**
     * @return devol_date
     */
    public Date getDevol_date() {
        return devol_date;
    }

    /**
     * @param devol_date new value of {@link #getDevol_date}
     */
    public void setDevol_date(Date devol_date) {
        this.devol_date = devol_date;
    }

}