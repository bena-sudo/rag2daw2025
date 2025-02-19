package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ieslluissimarro.rag.rag2daw2025.model.dto.ChatList;
import org.ieslluissimarro.rag.rag2daw2025.model.dto.PaginaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class FiltrosYEstadisticasService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PaginaDto<ChatList> executePaginatedQuery(String query, Pageable paging) {
        List<ChatList> allChats = executeQueryChatList(query);

        // Aplica la paginación manualmente
        int start = (int) paging.getOffset();
        int end = Math.min((start + paging.getPageSize()), allChats.size());
        List<ChatList> paginatedChats = allChats.subList(start, end);

        // Crea una página con los resultados paginados
        Page<ChatList> page = new PageImpl<>(paginatedChats, paging, allChats.size());

        // Convierte a PaginaDto
        return new PaginaDto<ChatList>(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.getContent(),
            page.getSort()
        );
    }

    private List<ChatList> executeQueryChatList(String query) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        // Mapa para eliminar duplicados por idChat
        Map<Long, ChatList> chatMap = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long idChat = (Long) row.get("id_chat");

            if (!chatMap.containsKey(idChat)) {
                ChatList chatList = new ChatList();
                chatList.setIdChat(idChat);
                chatList.setUsuario((String) row.get("chat_user"));
                chatList.setContexto((Integer) row.get("contexto"));
                chatMap.put(idChat, chatList);
            }
        }

        return new ArrayList<>(chatMap.values());
    }





    public List<Object[]> executeQuery(String sql) {
        try {
            Query query = entityManager.createNativeQuery(sql);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error ejecutando consulta en el service", e);
        }
    }

}
