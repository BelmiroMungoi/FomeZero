package com.bbm.fomezero.config;

import com.bbm.fomezero.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationDebugService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationDebugService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void debugUser(String email, String rawPassword) {
        System.out.println("=== DEBUG AUTENTICAÇÃO ===");
        System.out.println("Email recebido: " + email);
        System.out.println("Senha recebida: " + rawPassword);
        
        userRepository.findByEmail(email).ifPresentOrElse(
            user -> {
                System.out.println("Usuário encontrado: " + user.getEmail());
                System.out.println("Senha no BD: " + user.getPassword());
                System.out.println("Status: " + user.isStatus());
                
                boolean passwordMatches = passwordEncoder.matches(rawPassword, user.getPassword());
                System.out.println("Senha corresponde: " + passwordMatches);
                
                // Verifique o prefixo do hash
                if (user.getPassword().startsWith("$2a$") || 
                    user.getPassword().startsWith("$2b$") || 
                    user.getPassword().startsWith("$2y$")) {
                    System.out.println("Hash BCrypt: SIM");
                } else {
                    System.out.println("Hash BCrypt: NÃO - PROBLEMA ENCONTRADO!");
                }
            },
            () -> System.out.println("Usuário NÃO encontrado!")
        );
        System.out.println("===========================");
    }
}