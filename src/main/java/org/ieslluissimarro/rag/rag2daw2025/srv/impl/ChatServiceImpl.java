package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.exception.FiltroException;
import org.ieslluissimarro.rag.rag2daw2025.helper.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.helper.PaginationFactory;
import org.ieslluissimarro.rag.rag2daw2025.helper.PeticionListadoFiltradoConverter;
import org.ieslluissimarro.rag.rag2daw2025.model.db.ChatDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaResponse;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PeticionListadoFiltrado;
import org.ieslluissimarro.rag.rag2daw2025.repository.ChatRepository;
import org.ieslluissimarro.rag.rag2daw2025.repository.PreguntaRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.ChatService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mappers.ChatMapper;
import org.ieslluissimarro.rag.rag2daw2025.srv.specification.FiltroBusquedaSpecification;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;


import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

	private final ChatRepository chatRepository;
	private final PaginationFactory paginationFactory;
	private final PeticionListadoFiltradoConverter peticionConverter;

	public ChatServiceImpl(
		ChatRepository chatRepository,
		PaginationFactory paginationFactory,
		PeticionListadoFiltradoConverter peticionConverter
	) {
		this.chatRepository = chatRepository;
		this.paginationFactory = paginationFactory;
		this.peticionConverter = peticionConverter;
	}

	@Override
	public ChatInfo create(ChatList chatList) {
		

		ChatDb entity = ChatMapper.INSTANCE.ChatListToChatDb(chatList);
		ChatDb savedEntity = chatRepository.save(entity);

		return ChatMapper.INSTANCE.ChatDbToChatInfo(savedEntity);
	}

    @Override
    public List<PreguntaInfo> getChatPreguntasByIdChat(Long id) {
        ChatDb chatDb = chatRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("CHAT_NOT_FOUND", "No se encontro el chat con ID " + id));

        List<PreguntaDb> preguntasDb = preguntaRepository.findByIdChat_IdChat(id);

        return PreguntaMapper.INSTANCE.preguntasAPreguntaInfo(preguntasDb);



    }

	@Override
	public ChatEdit update(Long id, ChatEdit chatEdit) {

		if (!id.equals(chatEdit.getIdChat())) {
			throw new EntityIllegalArgumentException("CHAT_ID_MISSMATCH",
					"El id del chat que se ha dado no corresponde con ninguno en la base de datos.");
		}

		ChatDb existingEntity = chatRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("CHAT_NOT_FOUND", "No se ha econtrado el chat"));

		ChatMapper.INSTANCE.updateChatDbFromChatEdit(chatEdit, existingEntity);
		return ChatMapper.INSTANCE.ChatDbToChatEdit(existingEntity);

	}

	@Override
	public List<ChatList> findAllChatList() {
		List<ChatDb> listaChatList = chatRepository.findAll();
		return ChatMapper.INSTANCE.chatsToChatList(listaChatList);
	}

	@Override
	public void delete(Long id) {
		if (chatRepository.existsById(id)) {
			chatRepository.deleteById(id);
		}
	}

	@Override
	public PaginaResponse<ChatList> findAll(String[] filter, int page, int size, String[] sort) throws FiltroException {
		/** 'peticionConverter' está en el constructor del service porque utilizando una buena arquitectura
		toda clase externa al Service que contenga un método a ejecutar debería ser testeable de manera
		independiente y para ello debe de estar en el constructor para poderse mockear.**/

		PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
		return findAll(peticion);// En vez de hacer 2 veces el filtrado llamamos al método que lo centraliza
	}

	@Override
	public PaginaResponse<ChatList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {

		try {
			Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);

			Specification<ChatDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<ChatDb>(peticionListadoFiltrado.getListaFiltros());

			Page<ChatDb>page = chatRepository.findAll(filtrosBusquedaSpecification, pageable);

			return ChatMapper.pageToPaginaResponse(
				page,
				peticionListadoFiltrado.getListaFiltros(),
				peticionListadoFiltrado.getSort()
			);
		} catch (JpaSystemException ex) {
			String cause = "";
			if (ex.getRootCause() != null)
				if (ex.getCause().getMessage() != null)
					cause = ex.getRootCause().getMessage();
			
			throw new FiltroException("BAD_OPERATOR_FILTER", "Error: No se puede realizar esa operación sobre el atributo por el tipo de dato", ex.getMessage() + ":" + cause);

		} catch (PropertyReferenceException e) {
			throw new FiltroException("BAD_ATTRIBUTE_ORDER", "Error: No existe el nombre del atributo de ordenación en la tabla", e.getMessage());
		} catch (InvalidDataAccessApiUsageException e) {
			throw new FiltroException("BAD_ATTRIBUTE_FILTER", "Error: Posiblemente no existe el atributo en la tabla", e.getMessage());
		}
	}

	@Override
	public PaginaDto<ChatList> findAllPageChatInfo(List<FiltroBusqueda> listaFiltros, Pageable pageable) {
				Page<ChatDb> paginaChatRelaciones;

				if (listaFiltros.isEmpty()) {
					paginaChatRelaciones = chatRepository.findAll(pageable);
				} else {
					// Especificar filtro
					Specification<ChatDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(listaFiltros);
		
					// Filtrar y ordenar
					paginaChatRelaciones = chatRepository.findAll(filtrosBusquedaSpecification, pageable);
				}
		
				return new PaginaDto<>(
						paginaChatRelaciones.getNumber(),
						paginaChatRelaciones.getSize(),
						paginaChatRelaciones.getTotalElements(),
						paginaChatRelaciones.getTotalPages(),
						ChatMapper.INSTANCE.chatsToChatList(paginaChatRelaciones.getContent()),
						paginaChatRelaciones.getSort()
				);
	}

    @Override
    public List<ChatInfo> findAllChatInfoList() {
        List<ChatDb> listaChatList = chatRepository.findAll();
        return ChatMapper.INSTANCE.chatsToChatInfo(listaChatList);
    }

    @Override
    public ChatInfo findById(Long idChat) {
        ChatDb existingEntity = chatRepository.findById(idChat)
        .orElseThrow(() -> new EntityNotFoundException("CHAT_NOT_FOUND", "No se ha econtrado el chat"));

        return ChatMapper.INSTANCE.ChatDbToChatInfo(existingEntity);


    }

}
