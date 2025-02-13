package org.simarro.proyecto.chatbot.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PeticionListadoFiltrado {
    private List<FiltroBusqueda> listaFiltros;
    private Integer page;
    private Integer size;
    private List<String> sort;
}