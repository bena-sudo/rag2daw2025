package org.ieslluissimarro.rag.rag2daw2025.srv.mappers;

import java.util.List;

import org.ieslluissimarro.rag.rag2daw2025.model.db.ChatDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    ChatInfo ChatDbToChatInfo(ChatDb chatDb);
    ChatList ChatDbToChatList(ChatDb chatDb);
    ChatEdit ChatDbToChatEdit(ChatDb chatDb);
    ChatDb ChatEditToChatDb(ChatEdit chatEdit);
    List<ChatList> chatsToChatList(List<ChatDb> chatsDb);
    void updateChatDbFromChatEdit(ChatEdit chatEdit, @MappingTarget ChatDb existingEntity);
    ChatDb ChatListToChatDb(ChatList chatList);
}
