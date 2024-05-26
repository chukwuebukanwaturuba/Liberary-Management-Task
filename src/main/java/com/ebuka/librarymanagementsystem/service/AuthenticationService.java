package com.ebuka.librarymanagementsystem.service;

import com.ebuka.librarymanagementsystem.dtos.request.AuthenticationRequest;
import com.ebuka.librarymanagementsystem.dtos.response.AuthenticationResponse;
import com.ebuka.librarymanagementsystem.dtos.request.SignUpRequest;
import com.ebuka.librarymanagementsystem.model.entities.Patron;

public interface AuthenticationService {
    Patron signUp(SignUpRequest signUpRequest);
    Patron signUpAdmin(SignUpRequest signUpRequest);
    AuthenticationResponse signIn(AuthenticationRequest request);

}
