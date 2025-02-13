package org.simarro.proyecto.chatbot.service.specification;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.simarro.proyecto.chatbot.model.dto.FiltroBusqueda;
import org.simarro.proyecto.chatbot.service.specification.operacion.ContieneOperacionStrategy;
import org.simarro.proyecto.chatbot.service.specification.operacion.IgualOperacionStrategy;
import org.simarro.proyecto.chatbot.service.specification.operacion.MayorQueOperacionStrategy;
import org.simarro.proyecto.chatbot.service.specification.operacion.MenorQueOperacionStrategy;
import org.simarro.proyecto.chatbot.service.specification.operacion.OperacionBusquedaStrategy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class FiltroBusquedaSpecification<T> implements Specification<T> {
    
    private List<FiltroBusqueda> filtrosBusqueda;
    private final List<OperacionBusquedaStrategy> estrategias;

    private final List<OperacionBusquedaStrategy> DEFAULT_STRATEGIES=List.of(
        new IgualOperacionStrategy(),
        new ContieneOperacionStrategy(),
        new MayorQueOperacionStrategy(),
        new MenorQueOperacionStrategy()
    );

    public FiltroBusquedaSpecification(List<FiltroBusqueda> filtrosBusqueda) {
        this.filtrosBusqueda = filtrosBusqueda;
        this.estrategias = DEFAULT_STRATEGIES; // Si no se especifican, podemos asignar las operaciones por defecto
    }
    
    public FiltroBusquedaSpecification(List<FiltroBusqueda> filtrosBusqueda, List<OperacionBusquedaStrategy> estrategias) {
        this.filtrosBusqueda = filtrosBusqueda;
        this.estrategias = estrategias;
    }

    private Predicate crearPredicado(Root<?> root, CriteriaBuilder criteriaBuilder, FiltroBusqueda filtro) {
        return estrategias.stream()
            .filter(estrategia -> estrategia.soportaOperacion(filtro.getOperacion()))
            .findFirst()
            .map(estrategia -> estrategia.crearPredicado(root, criteriaBuilder, filtro))
            .orElseThrow(() -> new UnsupportedOperationException(
                "Operador de filtro no permitido: " + filtro.getOperacion()
            ));
    }

    @Override
    public Predicate toPredicate(
        @NonNull Root<T> root,
        @Nullable CriteriaQuery<?> query,
        @NonNull CriteriaBuilder criteriaBuilder
    ) {
        List<Predicate> predicados = filtrosBusqueda.stream()
            .map(filtro -> crearPredicado(root, criteriaBuilder, filtro))
            .collect(Collectors.toList());

        return predicados.isEmpty()
            ? criteriaBuilder.conjunction()
            : criteriaBuilder.and(predicados.toArray(new Predicate[0]));
    }
}