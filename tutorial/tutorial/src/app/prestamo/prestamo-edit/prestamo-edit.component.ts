import { Component, Inject,  OnInit } from '@angular/core';
import { Prestamo } from '../model/Prestamo';
import { PrestamoService } from '../prestamo.service';
import { Game } from 'src/app/game/model/Game';
import { GameService } from 'src/app/game/game.service';
import { Cliente } from 'src/app/cliente/model/Cliente';
import { ClienteService } from 'src/app/cliente/cliente.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';

import { DialogMessageComponent } from 'src/app/core/dialog-message/dialog-message.component';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-prestamo-edit',
  templateUrl: './prestamo-edit.component.html',
  styleUrls: ['./prestamo-edit.component.scss']
})

export class PrestamoEditComponent implements OnInit {
    
    prestamo: Prestamo;
    cliente: Cliente[];
    games: Game[];

    constructor(
        public dialogRef: MatDialogRef<PrestamoEditComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private gameService: GameService,
        private prestamoService: PrestamoService,
        private clienteService: ClienteService,
        public dialog: MatDialog
        
    ) { }

    ngOnInit(): void {

        this.prestamo = new Prestamo();
        
        this.clienteService.getClientes().subscribe(
            cliente => this.cliente = cliente
        );

        this.gameService.getGames().subscribe(
            games => this.games = games
        );
    }

    /**
     * Guarda el préstamo en la base de datos, previa validación.
     * 
     * En front:
     * -> Si la fecha de devolución es anterior a la de préstamo.
     * -> Si el préstamo excede en más de 14 días.
     * 
     * En backend:
     * -> Si el juego ha sido prestado a otro cliente en el rango de
     *    fechas seleccionado.
     * -> Si el cliente tiene prestados más de dos juegos en una misma
     *    fecha.
     */
    onSave() {

        var final_date = Math.abs((this.prestamo.prest_date.getTime() - this.prestamo.devol_date.getTime())) / (1000 * 3600 * 24);

        if (this.prestamo.devol_date < this.prestamo.prest_date){

            const dialogRef = this.dialog.open(DialogMessageComponent, {
                data: { title: "Error en la fecha de devolución",
                description: "La fecha de devolución seleccionada es anterior a la fecha inicial.<br>" 
                + "Seleccione el mismo día o una fecha posterior para devolver el juego."}
            });

            this.prestamo.devol_date = null;
        }
        else if (final_date >= 14 ) {

            const dialogRef = this.dialog.open(DialogMessageComponent, {
                data: { title: "Exceso de días del préstamo",
                description: "La fecha final excede en más de 14 días la fecha inicial.<br>" 
                + "Seleccione una fecha de devolución inferior a 14 días."}
            });

            this.prestamo.devol_date = null;
        }
        else {
            this.prestamoService.savePrestamo(this.prestamo).subscribe(response => {

                if (response == 100) {
                    const dialogRef = this.dialog.open(DialogMessageComponent, {
                        data: {
                            title: "Juego prestado",
                            description: "Este juego ya está prestado en el rango de fechas indicado.<br>" + 
                            "Seleccione un período de préstamo alternativo."
                        }
                    });

                    this.prestamo.prest_date = null;
                    this.prestamo.devol_date = null;
                }
                else if (response == 200) {
                    const dialogRef = this.dialog.open(DialogMessageComponent, {
                        data: {
                            title: "Cliente con dos o más préstamos",
                            description: "Este cliente tiene dos o más préstamos dentro de las fechas especificadas.<br>" + 
                            "Seleccione un período de préstamo alternativo."
                        }
                    });

                    this.prestamo.prest_date = null;
                    this.prestamo.devol_date = null;

                }
                else {
                    this.dialogRef.close();
                }
            });
        }
    }

    onClose() {
        this.dialogRef.close();
    }
}