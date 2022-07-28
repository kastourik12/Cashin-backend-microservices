package com.example.usersmicroservice.controllers;
import com.example.usersmicroservice.payload.request.LoginRequest;
import com.example.usersmicroservice.payload.request.SignupRequest;
import com.example.usersmicroservice.security.refreshToken.RefreshToken;
import com.example.usersmicroservice.security.refreshToken.RefreshTokenService;
import com.example.usersmicroservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.authenticateUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return authService.saveUser(signUpRequest);
	}
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<?> verifyUser(@PathVariable String token) {
		return authService.verifyUser(token);
	}

	@GetMapping("/refresh")
	public ResponseEntity<?> refreshAndGetAuthenticationToken(@RequestBody String token) {
		return authService.refreshAndGetAuthenticationToken(token);
	}

	@PostMapping("/signout")
	public ResponseEntity<?> signOut(@RequestBody String token) {
		return authService.signOut(token);
	}
}
