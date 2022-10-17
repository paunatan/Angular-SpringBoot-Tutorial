import { Cliente } from "src/app/cliente/model/Cliente";
import { Game } from "src/app/game/model/Game";

export class Prestamo {
    id: number;
    game: Game;
    cliente: Cliente;
    prest_date: Date;
    devol_date: Date;
}