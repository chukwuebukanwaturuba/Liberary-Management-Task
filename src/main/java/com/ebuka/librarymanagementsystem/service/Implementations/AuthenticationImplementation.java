package com.ebuka.librarymanagementsystem.service.Implementations;

import com.ebuka.librarymanagementsystem.dtos.request.AuthenticationRequest;
import com.ebuka.librarymanagementsystem.dtos.response.AuthenticationResponse;
import com.ebuka.librarymanagementsystem.dtos.request.SignUpRequest;
import com.ebuka.librarymanagementsystem.exception.ErrorStatus;
import com.ebuka.librarymanagementsystem.exception.LibraryManagementException;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import com.ebuka.librarymanagementsystem.model.enums.Role;
import com.ebuka.librarymanagementsystem.repositories.PatronRepository;
import com.ebuka.librarymanagementsystem.security.JwtUtils;
import com.ebuka.librarymanagementsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationImplementation implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final PatronRepository patronRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    @Override
    public Patron signUp(SignUpRequest signUpRequest) {
        Patron user = patronRepository.findByEmail(signUpRequest.getEmail());
        if (user != null){
            throw new LibraryManagementException(ErrorStatus.EMAIL_TAKEN_ERROR, "Email already taken");
        }
        Patron patron = setPatron(signUpRequest);
        patronRepository.save(patron);
        return patron;
    }

    @Override
    public Patron signUpAdmin(SignUpRequest signUpRequest) {
        Patron admin = patronRepository.findByEmail(signUpRequest.getEmail());
        if (admin != null){
            throw new LibraryManagementException(ErrorStatus.EMAIL_TAKEN_ERROR, "Email already taken");
        }
        Patron patron = setAdmin(signUpRequest); // Use a modified setAdmin method for admin registration
        patronRepository.save(patron);
        return patron;
    }

    @Override
    public AuthenticationResponse signIn(AuthenticationRequest request) {
        var user = patronRepository.findPatronByEmail(request.getEmail())
                .orElseThrow(() -> new LibraryManagementException(ErrorStatus.USER_NOT_FOUND_ERROR, "Account not found"));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));
        } catch (AuthenticationException e){
            log.error(e.getMessage());
            throw new LibraryManagementException(ErrorStatus.VALIDATION_ERROR, "Password and email does not match");
        }
        var jwt =jwtUtils.generateToken(user);
        return new AuthenticationResponse(jwt, "Successfully logged in");
    }


    private Patron setPatron(SignUpRequest request){
        Patron patron = new Patron();

        patron.setFullName(request.getFullName());
        patron.setEmail(request.getEmail());
        patron.setAddress(request.getAddress());
        patron.setPhone(request.getPhone());
        patron.setPassword(passwordEncoder.encode(request.getPassword()));
        patron.setRole(Role.PATRON);

        return patron;
    }
    private Patron setAdmin(SignUpRequest request){
        Patron patron = new Patron();

        patron.setFullName(request.getFullName());
        patron.setEmail(request.getEmail());
        patron.setAddress(request.getAddress());
        patron.setPhone(request.getPhone());
        patron.setPassword(passwordEncoder.encode(request.getPassword()));
        patron.setRole(Role.ADMIN); // Set role as ADMIN

        return patron;
    }
}
