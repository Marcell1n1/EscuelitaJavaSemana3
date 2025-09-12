package com.marcell.pedido_ms.repository;

import com.marcell.pedido_ms.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
