package com.marcell.pedido_ms.controller;

import com.marcell.pedido_ms.mapper.UserMapper;
import com.marcell.pedido_ms.model.dto.UserDto;
import com.marcell.pedido_ms.model.dto.UserSaveRequestDto;
import com.marcell.pedido_ms.model.dto.UserSaveResponseDto;
import com.marcell.pedido_ms.model.entity.User;
import com.marcell.pedido_ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return  ResponseEntity.ok(UserMapper.INSTANCE.map(userService.listAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.INSTANCE.map(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserSaveResponseDto> save(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return ResponseEntity.ok(UserMapper.INSTANCE.toUserSaveResponseDto(userService.save(UserMapper.INSTANCE.toEntity(userSaveRequestDto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,@RequestBody UserSaveRequestDto userUpdateRequestDto) {
        User incoming = UserMapper.INSTANCE.toEntity(userUpdateRequestDto);
        User updated = userService.update(id, incoming);
        return ResponseEntity.ok(UserMapper.INSTANCE.map(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
