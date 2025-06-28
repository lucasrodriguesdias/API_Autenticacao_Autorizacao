package com.example.authserver;

import com.example.authserver.repository.UserRepository;
import com.example.authserver.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository.findByUsername("mestre").ifPresentOrElse(
                user -> { /* Já existe */ },
                () -> {
                    com.example.authserver.model.User admin = new com.example.authserver.model.User(null, "mestre", passwordEncoder.encode("topchave321"), "ADMIN", "(31)98829-6306");
                    userRepository.save(admin);
                }
        );
        userRepository.findByUsername("usuarioPadrao").ifPresentOrElse(
                user -> { /* Já existe */ },
                () -> {
                    com.example.authserver.model.User regularUser = new com.example.authserver.model.User(null, "usuarioPadrao", passwordEncoder.encode("padrao321"), "USER","(31)98759-4202");
                    userRepository.save(regularUser);
                }
        );
    }

@Test
void testLoginSuccess() throws Exception {
    String token = mockMvc.perform(post("/auth/login")
            .param("username", "mestre")
            .param("password", "topchave321")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
           .andExpect(status().isOk())
           .andReturn().getResponse().getContentAsString();

    // Valida o token programaticamente
    assert jwtService.validateToken(token);

    // Confirma que ele não está vazio
    org.assertj.core.api.Assertions.assertThat(token).isNotEmpty();
}


    @Test
    void testLoginFailureInvalidPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                .param("username", "mestre")
                .param("password", "errada123")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isUnauthorized())
               .andExpect(content().string(containsString("Senha incorreta.")));
    }

    @Test
    void testProtectedEndpointAccessDeniedWithoutToken() throws Exception {
        mockMvc.perform(get("/api/hello"))
               .andExpect(status().isUnauthorized());
    }
  

    @Test
    void testProtectedAdminEndpointAccessDeniedWithUserToken() throws Exception {
        String userToken = mockMvc.perform(post("/auth/login")
                .param("username", "usuarioPadrao")
                .param("password", "padrao321")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/api/admin")
                .header("Authorization", "Bearer " + userToken))
               .andExpect(status().isForbidden());
    }
}
