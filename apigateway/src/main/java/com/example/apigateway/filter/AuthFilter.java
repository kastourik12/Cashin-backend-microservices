package com.example.apigateway.filter;


import com.kastourik12.clients.users.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private  Logger logger= LoggerFactory.getLogger(AuthFilter.class);
    private final WebClient.Builder webClientBuilder;
    @Value("${client.instancesUri.users}")
    private String authServiceUrl;



    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
        this.logger = LoggerFactory.getLogger(AuthFilter.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization information");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authHeader.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Incorrect authorization structure");
            }
            String token = parts[1];

            logger.info("inside auth filter : validating access token");
                return webClientBuilder.build()
                        .get()
                        .uri(authServiceUrl+"?token=" + token)
                        .retrieve().bodyToMono(UserDTO.class)
                        .map(userDto -> {
                            exchange.getRequest()
                                    .mutate()
                                    .header("x-auth-user-id", String.valueOf(userDto.getId()))
                                    .header("x-auth-user-login", String.valueOf(userDto.getLogin()));
                            return exchange;
                        }).flatMap(chain::filter);

        };
    }




    public static class Config {

    }
}
