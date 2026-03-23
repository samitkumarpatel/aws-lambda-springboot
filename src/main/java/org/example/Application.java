package org.example;

import org.example.controller.PingController;
import org.example.service.NameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SpringBootApplication
// We use direct @Import instead of @ComponentScan to speed up cold starts
// @ComponentScan(basePackages = "org.example.controller")
@Import({ PingController.class })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    List<String> names() {
        return new ArrayList<>();
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction(NameService nameService) {
        return RouterFunctions
                .route()
                .GET("/functional/ping", request -> ServerResponse.ok().body(Map.of("pong", "Hello World!")))
                .path("/functional/name", uriBuilder -> uriBuilder
                        .GET(request -> ServerResponse.ok().body(nameService.names()))
                        .POST(request -> {
                            var name = request.body(String.class);
                            nameService.add(name);
                            return ServerResponse.ok().build();
                        })
                        .build())
                .build();
    }
}