package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.exception.BindingResultException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityAlreadyExistsException;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;

public interface ChatService {

	/**
	 * Crea un nuevo Chat en el sistema.
	 *
	 * @param ChatEdit El objeto DTO que contiene los datos a introducir en un nuevo
	 *                 chat
	 * @return El objeto DTO del chat creado.
	 * @throws HttpMessageNotReadableException Si no coincide el tipo de dat>
	 * @throws BindingResultException          Si hay errores de validación en el
	 *                                         obj>
	 * @throws EntityAlreadyExistsException    Si ya existe un documento con el>
	 * @throws DataIntegrityViolationException Si hay errores al almacenar e>
	 */
	public ChatInfo create(ChatEdit chatEdit);

	/**
	 * Devuelve la informacion de un chat por su id
	 * 
	 * @param id Id del chat al que queremos acceder
	 * @return Un optional que contiene el chat si lo encuentra, en caso contrario
	 *         contiene vacio
	 */
	public ChatInfo getChatInfoById(Long id);

	/**
	 * Actualiza un chat existente en el sistema.
	 * 
	 * @param id       Id del chat a actualizar
	 * @param chatEdit La nueva información a introducir
	 * @return El objeto DTO modificado
	 */
	public ChatInfo update(Long id, ChatEdit chatEdit);

	/*
	 * Borra un chat de la base de datos
	 */
	public void delete(Long idchat);



	public List<ChatList> findAllChatList();
	// public List<ChatList> findAllChatList(Sort sort);
	// public @NonNull List<ChatDb> findByTitleContaining( String title, Sort sort);
}