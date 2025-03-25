package com.nuevospachile.gestion_tareas_api.controller;

import com.nuevospachile.gestiontareasapi.api.AuthenticationApi;
import com.nuevospachile.gestiontareasapi.model.JwtResponse;
import com.nuevospachile.gestiontareasapi.model.LoginRequest;
import com.nuevospachile.gestion_tareas_api.entity.User;
import com.nuevospachile.gestion_tareas_api.security.JwtTokenProvider;
import com.nuevospachile.gestion_tareas_api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthenticationApi {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public ResponseEntity<JwtResponse> authLoginPost(LoginRequest loginRequest) {
        User user = authService.validateCredentials(loginRequest.getUsername(), loginRequest.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtTokenProvider.generateToken(user);
        JwtResponse response = new JwtResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }
}
