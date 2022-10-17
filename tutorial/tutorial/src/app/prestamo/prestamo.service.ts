import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Pageable } from '../core/model/page/Pageable';
import { Prestamo } from './model/Prestamo';
import { PrestamoPage } from './model/PrestamoPage';

@Injectable({
    providedIn: 'root'
})

export class PrestamoService {

    constructor(
        private http: HttpClient
    ) { }

    /**
     * Obtiene los préstamos de juegos realizados.
     * 
     * 
     * @param pageable Objeto Angular Pageable. Permite
     *                 paginar los datos.
     * @returns Listado por páginas de los préstamos.
     */
    getPrestamos(customerId? :number, gameId? : number, searchDate?: Date,  pageable? : Pageable) : Observable<PrestamoPage> {

        return this.http.post<PrestamoPage>(this.composeFindUrl(customerId, gameId, searchDate), {pageable:pageable});
    }

    /**
     * Guarda un préstamo en la base de datos.
     * @param prestamo Préstamo a guardar.
     */
    savePrestamo(prestamo: Prestamo): Observable<any> {
        
        let url = 'http://localhost:8080/prestamo';
        if (prestamo.id != null) url += '/'+prestamo.id;

        return this.http.put(url, prestamo, {observe: 'body'});
    }

    /**
     * Borra un préstamo de la base de datos.
     * @param idPrestamo Id del préstamo a borrar.
     */
    deletePrestamo(idPrestamo: number): Observable<any> {
        
        return this.http.delete('http://localhost:8080/prestamo/'+idPrestamo);
    }

    /**
     * Envía la URL compuesta al backend para que devuelva los
     * préstamos en base al cliente, juego o fecha especificados.
     * 
     * @param customerId Id del cliente.
     * @param gameId Id del juego.
     * @returns URl completa a enviar.
     */
    private composeFindUrl(customerId?: number, gameId?: number, searchDateBeforeConv?: Date) : string {

        let params = '';
        let searchDate = '';

        if (customerId != null){
            params += 'customerId='+customerId;
        }

        if (gameId != null) {
            if (params != '') params += "&";
            params += "gameId=" +gameId;
        }

        if (searchDateBeforeConv != null) {
            if (params != '') params += "&";
            
            searchDate = searchDateBeforeConv.getFullYear().toString() + "-" 
            + (searchDateBeforeConv.getMonth()+1).toString() + "-" 
            + searchDateBeforeConv.getDate().toString() ;
            
            params += "searchDate=" + searchDate;
        }

        let url = 'http://localhost:8080/prestamo';

        if (params == '') return url;
            else return url + '?' + params;

    }
}