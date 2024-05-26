package com.ebuka.librarymanagementsystem.service.Implementations;

import com.ebuka.librarymanagementsystem.dtos.request.PatronUpdateRequest;
import com.ebuka.librarymanagementsystem.dtos.response.PatronResponse;
import com.ebuka.librarymanagementsystem.exception.ErrorStatus;
import com.ebuka.librarymanagementsystem.exception.LibraryManagementException;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import com.ebuka.librarymanagementsystem.repositories.PatronRepository;
import com.ebuka.librarymanagementsystem.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatronImplementation implements PatronService {
    private static final Logger logger = LoggerFactory.getLogger(PatronService.class);
    private final PatronRepository repository;
    @Override
    public List<Patron> getAllPatron(){
        try {
            List<Patron> patrons = repository.findAll();
            logger.info("Fetched {} patrons", patrons.size());
            return patrons;
        }catch (Exception e){
            logger.error("Error fetching patrons", e);
            throw new LibraryManagementException(ErrorStatus.GENERAL_ERROR, "Error fetching patrons");
        }
    }

    @Override
    public PatronResponse getPatronById(Long id) {
        Patron patron = findPatronById(id);
        return new PatronResponse(patron.getId(), patron.getFullName(), patron.getEmail(), patron.getAddress(), patron.getPhone());
    }

    @Override
    public PatronResponse updatePatronInfo(Long id, PatronUpdateRequest request) {
        Patron patron = findPatronById(id);

        if (request.getFullName() == null) {
            request.setFullName(patron.getFullName());
        }
        if (request.getAddress() == null) {
            request.setAddress(patron.getAddress());
        }
        if (request.getPhone() == null) {
            request.setPhone(patron.getPhone());
        }

        patron.setFullName(request.getFullName());
        patron.setPhone(request.getPhone());
        patron.setAddress(request.getAddress());

        Patron updatedPatron = repository.save(patron);


        return mapPatronToPatronResponse(updatedPatron);
    }

    @Override
    public String deletePatronById(Long id) {
        try {
            Patron patron = findPatronById(id);
            repository.delete(patron);
            return "Patron successfully deleted";
        } catch (Exception e) {
            System.err.println("Error deleting patron: " + e.getMessage());
            return "Error deleting Patron";
        }

    }

    private PatronResponse mapPatronToPatronResponse(Patron patron) {
        return new PatronResponse(patron.getId(), patron.getFullName(), patron.getEmail(), patron.getAddress(), patron.getPhone());
    }
    private Patron findPatronById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LibraryManagementException(ErrorStatus.PATRON_NOT_FOUND_ERROR, "Patron not found"));
    }
}
