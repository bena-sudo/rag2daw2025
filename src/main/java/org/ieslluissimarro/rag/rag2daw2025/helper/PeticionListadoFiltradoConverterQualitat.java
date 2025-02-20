package org.ieslluissimarro.rag.rag2daw2025.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PeticionListadoFiltrado;
import org.springframework.stereotype.Component;

@Component
public class PeticionListadoFiltradoConverterQualitat {
	
	private final FiltroBusquedaFactoryQualitat filtroBusquedaFactory;

	public PeticionListadoFiltradoConverterQualitat(FiltroBusquedaFactoryQualitat filtroBusquedaFactory) {
		this.filtroBusquedaFactory = filtroBusquedaFactory;
	}

	
	public PeticionListadoFiltrado convertFromParams(
		String[] filter, 
		int page, 
		int size, 
		String[] sort
	) throws FiltroException {

		List<FiltroBusquedaQualitat> filtros = filtroBusquedaFactory.crearListaFiltrosBusqueda(filter);

		List<String> sortList = (sort != null) ? Arrays.asList(sort) : Collections.emptyList();
		
		return new PeticionListadoFiltrado(filtros, page, size, sortList);
	}
}
