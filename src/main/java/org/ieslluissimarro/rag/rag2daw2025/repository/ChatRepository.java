package org.ieslluissimarro.rag.rag2daw2025.repository;

import org.ieslluissimarro.rag.rag2daw2025.model.db.ChatDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatRepository extends JpaRepository<ChatDb, Long>, JpaSpecificationExecutor<ChatDb> {

}
