package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;
import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.ChatDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.springframework.data.domain.Sort;

import io.micrometer.common.lang.NonNull;

public interface ChatService {
    public Optional<ChatInfo> getChatInfoById( Long id);
    public List<ChatList> findAllChatList();
    public List<ChatList> findAllChatList( Sort sort);
    //public @NonNull List<ChatDb> findByTitleContaining( String title,  Sort sort);
}