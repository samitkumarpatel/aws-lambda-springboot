package org.example.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NameService {
    private final List<String> names;

    public NameService(List<String> names) {
        this.names = names;
    }

    public List<String> names() {
        return names;
    }

    public void add(String name) {
        names.add(name);
    }
}
