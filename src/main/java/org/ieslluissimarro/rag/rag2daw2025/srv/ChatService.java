package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.springframework.data.domain.Sort;


public interface ChatService {

    public ChatEdit create(ChatEdit chatEdit);
    public Optional<ChatInfo> getChatInfoById( Long id);
    public List<ChatList> findAllChatList();
    public List<ChatList> findAllChatList( Sort sort);
    //public @NonNull List<ChatDb> findByTitleContaining( String title,  Sort sort);
    public ChatEdit initialMessageChat(String mensaje);
}