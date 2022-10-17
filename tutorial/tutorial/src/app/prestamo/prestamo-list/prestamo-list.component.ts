import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { PrestamoEditComponent } from '../prestamo-edit/prestamo-edit.component';
import { ClienteService } from 'src/app/cliente/cliente.service';
import { Cliente } from 'src/app/cliente/model/Cliente';
import { PrestamoService } from '../prestamo.service';
import { Prestamo } from '../model/Prestamo';
import { GameService } from 'src/app/game/game.service';
import { Game } from 'src/app/game/model/Game';


@Component({
  selector: 'app-prestamo-list',
  templateUrl: './prestamo-list.component.html',
  styleUrls: ['./prestamo-list.component.scss']
})

export class PrestamoListComponent implements OnInit {

    pageNumber: number = 0;
    pageSize: number = 5;
    totalElements: number = 0;

    cliente : Cliente[];
    games : Game[];
    filterCliente: Cliente;
    filterGame: Game;
    clienteId: number;
    gameId: number;
    searchDate: Date;
    dataSource = new MatTableDataSource<Prestamo>();
    displayedColumns: string[] = ['id','game','cliente','prest_date','devol_date','action'];

    constructor(
        private prestamoService: PrestamoService,
        private clienteService: ClienteService,
        private gameService : GameService,
        public dialog: MatDialog,
    ) { }

    ngOnInit(): void {

        this.clienteService.getClientes().subscribe(
            cliente => this.cliente = cliente
        );

        this.gameService.getGames().subscribe(
            games => this.games = games
        );

        this.loadPage();
    }

    createPrestamo() {
        
        const dialogRef = this.dialog.open(PrestamoEditComponent, {
            data: {}
        });

        dialogRef.afterClosed().subscribe(result => {

            this.ngOnInit();
        });
    }

    
    deletePrestamo(prestamo: Prestamo) {
        const dialogRef = this.dialog.open(DialogConfirmationComponent, {
            data: { title: "Eliminar préstamo", description: "Atención: si elimina el préstamo, se perderán sus datos.<br>¿Desea eliminar el préstamo?" }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.prestamoService.deletePrestamo(prestamo.id).subscribe(result => {
                    this.ngOnInit();
                });
            }
        });

    }

    onSearch(): void {

        this.clienteId = this.filterCliente != null ? this.filterCliente.id : null;
        this.gameId = this.filterGame != null ? this.filterGame.id : null;

        this.loadPage();
    }

    onCleanFilter(): void {

        this.clienteId = null;
        this.gameId = null;
        this.searchDate = null;

        this.filterCliente = null;
        this.filterGame = null;

        this.loadPage();
    }

    loadPage(event?: PageEvent) {

        let pageable: Pageable = {
            pageNumber: this.pageNumber,
            pageSize: this.pageSize,
            sort: [{
                property: 'id',
                direction: 'ASC'
            }]
        }

        if (event != null) {
            pageable.pageSize = event.pageSize;
            pageable.pageNumber = event.pageIndex;
        }

        this.prestamoService.getPrestamos(this.clienteId, this.gameId, this.searchDate, pageable).subscribe(data => {
            this.dataSource.data = data.content;
            this.pageNumber = data.pageable.pageNumber;
            this.pageSize = data.pageable.pageSize;
            this.totalElements = data.totalElements;
        })
    }
}