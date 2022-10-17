import { Cliente } from "src/app/cliente/model/Cliente";
import { PrestamoPage } from "./PrestamoPage";

export const PRESTAMO_DATA: PrestamoPage = {
    content: [
        { id: 1, cliente:{ id: 1, name: 'Cliente 1' }},
        { id: 2, cliente:{ id: 2, name: 'Cliente 2' }},
        { id: 3, cliente:{ id: 3, name: 'Cliente 3' }},
        { id: 4, cliente:{ id: 4, name: 'Cliente 4' }},
        { id: 5, cliente:{ id: 5, name: 'Cliente 5' }},
        { id: 6, cliente:{ id: 6, name: 'Cliente 6' }},
        { id: 7, cliente:{ id: 7, name: 'Cliente 7' }},
    ],  
    pageable : {
        pageSize: 5,
        pageNumber: 0,
        sort: [
            {property: "id", direction: "ASC"}
        ]
    },
    totalElements: 7
}