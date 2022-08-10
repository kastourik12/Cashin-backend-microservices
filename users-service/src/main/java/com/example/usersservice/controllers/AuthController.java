package com.example.usersservice.controllers;

import com.example.usersservice.payload.request.LoginRequest;
import com.example.usersservice.payload.request.SignupRequest;
import com.example.usersservice.services.AuthService;
import com.kastourik12.clients.users.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	@PostMapping ("/validateToken")
	public ResponseEntity<UserDTO> validateToken(@RequestParam(value = "token") String token) {
		logger.info("inside Auth microserivce : validating token");
		return authService.validateToken(token);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		logger.info("inside Auth microserivce : authenticating user");
		return authService.authenticateUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		logger.info("inside Auth microserivce : registering user");
		return authService.saveUser(signUpRequest);
	}
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<?> verifyUser(@PathVariable String token) {
		logger.info("inside Auth microserivce : verifying user");
		return authService.verifyUser(token);
	}

	@GetMapping("/refresh")
	public ResponseEntity<?> refreshAndGetAuthenticationToken(@RequestBody String token) {
		logger.info("inside Auth microserivce : refreshing token");
		return authService.refreshAndGetAuthenticationToken(token);
	}

	@PostMapping("/signout")
	public ResponseEntity<?> signOut(@RequestBody String token) {

		return authService.signOut(token);
	}
}
