package org.example;

import org.example.client.JsonPlaceHolderClient;
import org.example.controller.PingController;
import org.example.service.NameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
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
    JsonPlaceHolderClient jsonPlaceHolderClient(RestClient.Builder restClientBuilder) {
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builder()
                .exchangeAdapter(RestClientAdapter.create(restClientBuilder.build()))
                .build();
        return proxyFactory.createClient(JsonPlaceHolderClient.class);
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction(NameService nameService, JsonPlaceHolderClient jsonPlaceHolderClient) {
        return RouterFunctions
                .route()
                .GET("/functional/ping", request -> ServerResponse.ok().body(Map.of("pong", "Hello World functional!")))
                .path("/functional/name", uriBuilder -> uriBuilder
                        .GET(request -> ServerResponse.ok().body(Map.of("names", nameService.names())))
                        .POST(request -> {
                            var requestBody = request.body(Map.class);
                            if (requestBody.containsKey("name")) {
                                nameService.add(requestBody.get("name").toString());
                                return ServerResponse.ok().build();
                            } else {
                                return ServerResponse.badRequest().body(Map.of("error", "Missing 'name' in request body"));
                            }
                        })
                        .build())
                .path("/json-placeholder", uriBuilder -> uriBuilder
                        .GET("/user", request -> ServerResponse.ok().body(jsonPlaceHolderClient.allUser()))
                        .build())
                .build();
    }
}