package org.simarro.proyecto.chatbot.service.specification.operacion;

import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.simarro.proyecto.chatbot.model.enums.TipoOperacionBusqueda;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ContieneOperacionStrategy implements OperacionBusquedaStrategy {   
    @Override
    public Predicate crearPredicado(
        Root<?> root,
        CriteriaBuilder criteriaBuilder,
        FiltroBusqueda filtro
    ) {
        return criteriaBuilder.like(
            root.get(filtro.getAtributo()),
            "%" + filtro.getValor() + "%"
        );
    }

    @Override
    public boolean soportaOperacion(TipoOperacionBusqueda operacion) {
        return operacion == TipoOperacionBusqueda.CONTIENE;
    }
}