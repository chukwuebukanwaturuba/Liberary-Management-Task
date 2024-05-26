package com.ebuka.librarymanagementsystem.repositories;

import com.ebuka.librarymanagementsystem.model.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    Optional<Patron> findPatronByEmail(String email);

    Patron findByEmail(String email);

}
