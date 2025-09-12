package com.marcell.pedido_ms.service;

import com.marcell.pedido_ms.model.entity.User;

import java.util.List;

public interface UserService {

    public List<User> listAll();

    public User findById(Long id);

    public User save(User user);

    public User update(Long id, User user);

    public void deleteById(Long id);
}
