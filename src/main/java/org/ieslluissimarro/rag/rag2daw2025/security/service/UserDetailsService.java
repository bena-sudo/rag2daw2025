package org.ieslluissimarro.rag.rag2daw2025.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    
    public UserDetails loadUserByEmail(String email);
}
