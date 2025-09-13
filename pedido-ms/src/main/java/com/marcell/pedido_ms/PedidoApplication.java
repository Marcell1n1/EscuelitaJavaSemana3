package com.marcell.pedido_ms;

;
import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients(basePackages = "com.marcell.pedido_ms.client")
public class PedidoApplication {
    public static void main(String[] args) { SpringApplication.run(PedidoApplication.class, args); }
}