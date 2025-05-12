package ru.korevg.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthController {



    @PostMapping("/token")
    public String getToken(@RequestParam String username, @RequestParam String password) {
        try {

            // Здесь вы можете сгенерировать и вернуть JWT токен
            return "Токен сгенерирован"; // Замените на фактическую логику генерации токена
        } catch (AuthenticationException e) {
            return "Ошибка аутентификации: " + e.getMessage();
        }
    }
}