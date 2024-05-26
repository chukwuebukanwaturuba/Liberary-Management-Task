package com.ebuka.librarymanagementsystem.service;

import com.ebuka.librarymanagementsystem.dtos.request.PatronUpdateRequest;
import com.ebuka.librarymanagementsystem.dtos.response.PatronResponse;
import com.ebuka.librarymanagementsystem.model.entities.Patron;

import java.util.List;

public interface PatronService {
    List<Patron> getAllPatron();

    PatronResponse getPatronById(Long id);

    PatronResponse updatePatronInfo(Long id, PatronUpdateRequest request);

    String deletePatronById(Long id);


}
