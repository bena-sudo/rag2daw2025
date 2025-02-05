package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.util.Optional;

import org.ieslluissimarro.rag.rag2daw2025.model.db.ChatDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.repository.ChatRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.ChatService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mappers.ChatMapper;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public ChatInfo create(ChatEdit chatEdit) {
        ChatDb entity = ChatMapper.INSTANCE.ChatEditToChatDb(chatEdit);
        ChatDb savedEntity = chatRepository.save(entity);

        return ChatMapper.INSTANCE.ChatDbToChatInfo(savedEntity);
    }

    @Override
    public ChatInfo getChatInfoById(Long id) {
        ChatDb chatDb = chatRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("CHAT_NOT_FOUND", "No se encontro el chat ocn ID " + id));

        return ChatMapper.INSTANCE.ChatDbToChatInfo(chatDb);        
    }

    @Override
    public ChatInfo update(Long id, ChatEdit chatEdit) {
        ChatDb chatDb = chatRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("CHAT_NOT_FOUND", "No se encontro el chat ocn ID " + id));

        // Terminar la función
    }

    @Override
    public ChatEdit create(ChatEdit chatEdit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public ChatEdit initialMessageChat(String mensaje) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialMessageChat'");
    }
    
}
