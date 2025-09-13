package com.marcell.pedido_ms.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String status;

    private String fechaRegistro;
}
