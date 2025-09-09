package com.nttdata.dockerized.postgresql.service;

import com.nttdata.dockerized.postgresql.model.entity.User;
import com.nttdata.dockerized.postgresql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente no encontrado"));
    }

    @Override
    public User save(User user) {
        user.setActive(Boolean.TRUE);
        user.setFechaRegistro(LocalDateTime.now());
        return userRepository.save(user);
    }
    @Override
    public User update(Long id, User user) {
        User db = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CLiente no encontrado para actualizar datos!"));
        if (user.getName() != null)  db.setName(user.getName());
        if (user.getEmail() != null) db.setEmail(user.getEmail());
        if (user.getActive() != null) db.setActive(user.getActive());
        return userRepository.save(db);
    }

    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado para eliminar!");
        }
        userRepository.deleteById(id);
    }
}
