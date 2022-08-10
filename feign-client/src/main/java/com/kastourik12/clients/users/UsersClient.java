package com.kastourik12.clients.users;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "users",path = "api/v1/payment/")
public interface UsersClient {
    @GetMapping("/validateToken")
    ResponseEntity<UserDTO> validateToken(@RequestParam String token);

}
