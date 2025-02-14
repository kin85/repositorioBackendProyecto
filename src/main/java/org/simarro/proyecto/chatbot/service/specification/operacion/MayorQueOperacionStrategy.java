package org.simarro.proyecto.chatbot.service.specification.operacion;

import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.simarro.proyecto.chatbot.model.enums.TipoOperacionBusqueda;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class MayorQueOperacionStrategy implements OperacionBusquedaStrategy {
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Predicate crearPredicado(
        Root<?> root,
        CriteriaBuilder criteriaBuilder,
        FiltroBusqueda filtro
    ) {
        return criteriaBuilder.greaterThan(
            root.get(filtro.getAtributo()),
            (Comparable) filtro.getValor()
        );
    }

    @Override
    public boolean soportaOperacion(TipoOperacionBusqueda operacion) {
        return operacion == TipoOperacionBusqueda.MAYOR_QUE;
    }
}
