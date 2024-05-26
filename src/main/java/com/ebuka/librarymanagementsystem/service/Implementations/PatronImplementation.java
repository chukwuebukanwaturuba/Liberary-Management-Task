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

/**
 * Service responsible for managing patron-related operations.
 * Implements the PatronService interface to handle patron retrieval, updating, and deletion.
 *
 * This service provides methods to fetch, update, and delete patron information. It interacts with
 * a repository to manage the underlying data and logs operations for monitoring purposes.
 *
 * Author: Chukwuebuka
 */
@Service
@RequiredArgsConstructor
public class PatronImplementation implements PatronService {
    private static final Logger logger = LoggerFactory.getLogger(PatronService.class);
    private final PatronRepository repository;

    /**
     * Retrieves all patrons from the library system.
     *
     * @return List of all patrons
     */
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


    /**
     * Retrieves a patron by their ID.
     *
     * @param id Long representing the patron ID
     * @return PatronResponse containing patron details
     */
    @Override
    public PatronResponse getPatronById(Long id) {
        Patron patron = findPatronById(id);
        return new PatronResponse(patron.getId(), patron.getFullName(), patron.getEmail(), patron.getAddress(), patron.getPhone());
    }

    /**
     * Updates the information of an existing patron.
     *
     * @param id Long representing the patron ID
     * @param request PatronUpdateRequest object containing updated patron details
     * @return PatronResponse with updated patron details
     */
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

    /**
     * Deletes a patron by their ID.
     *
     * @param id Long representing the patron ID
     * @return String indicating the status of the deletion
     */
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

    /**
     * Maps a Patron object to a PatronResponse object.
     *
     * @param patron Patron object to be mapped
     * @return PatronResponse containing patron details
     */
    private PatronResponse mapPatronToPatronResponse(Patron patron) {
        return new PatronResponse(patron.getId(), patron.getFullName(), patron.getEmail(), patron.getAddress(), patron.getPhone());
    }


    /**
     * Finds a patron by their ID.
     *
     * @param id Long representing the patron ID
     * @return Patron object if found
     */
    private Patron findPatronById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LibraryManagementException(ErrorStatus.PATRON_NOT_FOUND_ERROR, "Patron not found"));
    }
}
