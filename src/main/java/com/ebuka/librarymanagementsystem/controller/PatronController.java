package com.ebuka.librarymanagementsystem.controller;

import com.ebuka.librarymanagementsystem.dtos.request.PatronUpdateRequest;
import com.ebuka.librarymanagementsystem.dtos.response.PatronResponse;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import com.ebuka.librarymanagementsystem.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/patron")
public class PatronController {
    private final PatronService patronService;

    @GetMapping("/all-patron")
    public ResponseEntity<List<Patron>> getAllPatrons() {
        try {
            List<Patron> patrons = patronService.getAllPatron();
            if (patrons.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(patrons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-by-id/{id}")
    public PatronResponse getPatronById(@PathVariable Long id) {

        return patronService.getPatronById(id);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<PatronResponse> updatePatronInfo(@PathVariable Long id, @RequestBody PatronUpdateRequest request) {
        PatronResponse response = patronService.updatePatronInfo(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletePatron/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try {
            String result = patronService.deletePatronById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error deleting patron", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
