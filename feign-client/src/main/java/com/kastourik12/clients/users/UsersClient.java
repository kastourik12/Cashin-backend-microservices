package com.kastourik12.clients.users;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "users-service",
        url = "http://users/",
        path = "api/v1/users")
public interface UsersClient {
    @GetMapping("/getBalance")
    ResponseEntity<Double> getBalance(@RequestParam("userId") String userId);

}
