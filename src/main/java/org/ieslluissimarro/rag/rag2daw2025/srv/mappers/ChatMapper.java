package org.ieslluissimarro.rag.rag2daw2025.srv.mappers;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.helper.FiltroBusqueda;
import org.ieslluissimarro.rag.rag2daw2025.model.db.ChatDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);


    ChatInfo ChatDbToChatInfo(ChatDb chatDb);
    ChatList ChatDbToChatList(ChatDb chatDb);
    ChatEdit ChatDbToChatEdit(ChatDb chatDb);
    ChatDb ChatEditToChatDb(ChatEdit chatEdit);
    List<ChatList> chatsToChatList(List<ChatDb> chatsDb);
    void updateChatDbFromChatEdit(ChatEdit chatEdit, @MappingTarget ChatDb existingEntity);
    
    @Mapping(target = "idChat", ignore = true) // No mapeamos el idChat porque es autogenerado
    @Mapping(target = "fecha", ignore = true) 
    ChatDb ChatListToChatDb(ChatList chatList);

    // Convierte una pagina de chats en una respuesta paginada
    static PaginaResponse<ChatList> pageToPaginaResponse(
            Page<ChatDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                ChatMapper.INSTANCE.chatsToChatList(page.getContent()),
                filtros,
                ordenaciones);
    }
}
