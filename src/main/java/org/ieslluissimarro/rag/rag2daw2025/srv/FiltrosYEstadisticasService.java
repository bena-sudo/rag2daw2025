package org.ieslluissimarro.rag.rag2daw2025.srv;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class FiltrosYEstadisticasService {


    @PersistenceContext
    private EntityManager entityManager;


    public List<Object[]> executeQuery(String sql) {
        try {
            Query query = entityManager.createNativeQuery(sql);  
            return query.getResultList();  
        } catch (Exception e) {
            throw new RuntimeException("Error ejecutando consulta en el service", e);
        }
    }
}
