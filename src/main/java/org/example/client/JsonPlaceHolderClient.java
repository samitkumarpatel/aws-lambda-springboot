package org.example.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = "https://jsonplaceholder.typicode.com")
public interface JsonPlaceHolderClient {
    @GetExchange("/users")
    List<User> allUser();
}

record User(String id, String name, String username, String email, String phone) {}