package com.ebuka.librarymanagementsystem.controller;

import com.ebuka.librarymanagementsystem.dtos.request.AuthenticationRequest;
import com.ebuka.librarymanagementsystem.dtos.response.AuthenticationResponse;
import com.ebuka.librarymanagementsystem.dtos.request.SignUpRequest;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import com.ebuka.librarymanagementsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/sign-up")
    public ResponseEntity<Patron> signUp(@Valid @RequestBody SignUpRequest request){
        Patron patron = service.signUp(request);
        return new ResponseEntity<>(patron, HttpStatus.CREATED);
    }

    @PostMapping("/admin-signUp")
    public Patron signUpAdmin(@RequestBody SignUpRequest signUpRequest) {
        return service.signUpAdmin(signUpRequest);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = service.signIn(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
