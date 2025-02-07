package org.ieslluissimarro.rag.rag2daw2025.srv.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ieslluissimarro.rag.rag2daw2025.exception.EntityIllegalArgumentException;
import org.ieslluissimarro.rag.rag2daw2025.exception.EntityNotFoundException;
import org.ieslluissimarro.rag.rag2daw2025.model.db.ChatDb;
import org.ieslluissimarro.rag.rag2daw2025.model.db.PreguntaDb;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatEdit;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatInfo;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PreguntaInfo;
import org.ieslluissimarro.rag.rag2daw2025.repository.ChatRepository;
import org.ieslluissimarro.rag.rag2daw2025.repository.PreguntaRepository;
import org.ieslluissimarro.rag.rag2daw2025.srv.ChatService;
import org.ieslluissimarro.rag.rag2daw2025.srv.mappers.ChatMapper;
import org.ieslluissimarro.rag.rag2daw2025.srv.mappers.PreguntaMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final PreguntaRepository preguntaRepository;
 

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

}
