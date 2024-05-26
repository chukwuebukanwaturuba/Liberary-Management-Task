package com.ebuka.librarymanagementsystem.service.Implementations;

import com.ebuka.librarymanagementsystem.dtos.request.AuthenticationRequest;
import com.ebuka.librarymanagementsystem.dtos.request.SignUpRequest;
import com.ebuka.librarymanagementsystem.dtos.response.AuthenticationResponse;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import com.ebuka.librarymanagementsystem.model.enums.Role;
import com.ebuka.librarymanagementsystem.repositories.PatronRepository;
import com.ebuka.librarymanagementsystem.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationImplementationTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthenticationImplementation authenticationImplementation;

    private Patron patron;

    @BeforeEach
//    void setUp() {
//        patron = new Patron(1L, "John Doe", "john@gmail.com", "password123", "123 Main St", "1234567890", Role.PATRON);
//    }

    @Test
    void signUp() {
        SignUpRequest signUpRequest = new SignUpRequest("John Doe", "john@gmail.com", "password123", "123 Main St", "1234567890");
        when(patronRepository.findByEmail(signUpRequest.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");

        Patron result = authenticationImplementation.signUp(signUpRequest);

        assertNotNull(result);
        assertEquals(signUpRequest.getFullName(), result.getFullName());
        assertEquals(signUpRequest.getEmail(), result.getEmail());
        assertEquals(signUpRequest.getAddress(), result.getAddress());
        assertEquals(signUpRequest.getPhone(), result.getPhone());
        assertNotEquals(signUpRequest.getPassword(), result.getPassword());
        assertEquals(Role.PATRON, result.getRole());
        verify(patronRepository, times(1)).save(any(Patron.class));
    }

    @Test
    void signIn() {
        AuthenticationRequest request = new AuthenticationRequest("john@gmail.com", "password123");
        when(patronRepository.findPatronByEmail(request.getEmail())).thenReturn(Optional.of(patron));
        when(jwtUtils.generateToken(patron)).thenReturn("generatedToken");

        AuthenticationResponse result = authenticationImplementation.signIn(request);

        assertNotNull(result);
        assertEquals("generatedToken", result.getToken());
        assertEquals("Successfully logged in", result.getMessage());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}