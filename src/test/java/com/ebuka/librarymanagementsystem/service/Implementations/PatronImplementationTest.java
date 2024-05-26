package com.ebuka.librarymanagementsystem.service.Implementations;

import com.ebuka.librarymanagementsystem.dtos.request.PatronUpdateRequest;
import com.ebuka.librarymanagementsystem.dtos.response.PatronResponse;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import com.ebuka.librarymanagementsystem.model.enums.Role;
import com.ebuka.librarymanagementsystem.repositories.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatronImplementationTest {

    @Mock
    private PatronRepository repository;

    @InjectMocks
    PatronImplementation patronImplementation;

    private Patron patron;
    private List<Patron> patronList;

    @BeforeEach
    void setUp() {
        patron = new Patron(1L, "Chukwuebuka Nwaturuba", "ebusClement@gmail.com", "Ebus@123", "Lagos Nigeria", "1234567890", Role.ADMIN);
        patronList = new ArrayList<>();
        patronList.add(patron);
    }


    @Test
    void testGetAllPatron() {
        when(repository.findAll()).thenReturn(patronList);

        List<Patron> result = patronImplementation.getAllPatron();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getPatronById() {
        when(repository.findById(1L)).thenReturn(Optional.of(patron));

        PatronResponse result = patronImplementation.getPatronById(1L);

        assertNotNull(result);
        assertEquals(patron.getId(), result.getId());
        assertEquals(patron.getFullName(), result.getFullName());
        assertEquals(patron.getEmail(), result.getEmail());
        assertEquals(patron.getAddress(), result.getAddress());
        assertEquals(patron.getPhone(), result.getPhone());
    }

    @Test
    void updatePatronInfo() {
        when(repository.findById(1L)).thenReturn(Optional.of(patron));
        when(repository.save(any(Patron.class))).thenReturn(patron);

        PatronUpdateRequest request = new PatronUpdateRequest("Wisdom Solomon", "wisdom@gmail.com", "9876543210");
        PatronResponse result = patronImplementation.updatePatronInfo(1L, request);

        assertNotNull(result);
        assertEquals(patron.getId(), result.getId());
        assertEquals(request.getFullName(), result.getFullName());
        assertEquals(request.getAddress(), result.getAddress());
        assertEquals(request.getPhone(), result.getPhone());
        verify(repository, times(1)).save(any(Patron.class));
    }

    @Test
    void testDeletePatronByIdSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(patron));

        String result = patronImplementation.deletePatronById(1L);

        assertEquals("Patron successfully deleted", result);
        verify(repository, times(1)).delete(patron);
    }
}