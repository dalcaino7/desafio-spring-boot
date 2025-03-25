package com.nuevospachile.gestion_tareas_api.controller;

import com.nuevospachile.gestiontareasapi.api.UsersApi;
import com.nuevospachile.gestiontareasapi.model.NewUser;
import com.nuevospachile.gestiontareasapi.model.UserDTO;
import com.nuevospachile.gestion_tareas_api.entity.User;
import com.nuevospachile.gestion_tareas_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController implements UsersApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<UserDTO>> usersGet() {
        List<User> userList = userService.findAllUsers();
        List<UserDTO> dtos = userList.stream()
                .map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(u.getId().intValue());
                    dto.setUsername(u.getUsername());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<Void> usersPost(NewUser newUser) {
        User u = new User();
        u.setUsername(newUser.getUsername());
        u.setPassword(newUser.getPassword());
        userService.createUser(u);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<UserDTO> usersUserIdGet(Integer userId) {
        User found = userService.findUserById(userId.longValue());
        UserDTO dto = new UserDTO();
        dto.setId(found.getId().intValue());
        dto.setUsername(found.getUsername());
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> usersUserIdPut(Integer userId, NewUser newUser) {
        User data = new User();
        data.setUsername(newUser.getUsername());
        data.setPassword(newUser.getPassword());
        userService.updateUser(userId.longValue(), data);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> usersUserIdDelete(Integer userId) {
        userService.deleteUser(userId.longValue());
        return ResponseEntity.noContent().build();
    }
}
