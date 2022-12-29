package ru.clevertec.ecl.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class CheckController {

    @GetMapping
    public ResponseEntity<?> check() {
        return ResponseEntity.ok().build();
    }
}
