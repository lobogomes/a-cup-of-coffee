package br.com.alterdata.vendas.config;

import br.com.alterdata.vendas.model.entity.Usuario;
import br.com.alterdata.vendas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    @Autowired
    UsuarioRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init() {
        return args -> {
            userRepository.save(new Usuario(1L, "admin", passwordEncoder.encode("admin"), "ADMIN"));
            userRepository.save(new Usuario(2L, "user", passwordEncoder.encode("user"), "USER"));
        };
    }
}
