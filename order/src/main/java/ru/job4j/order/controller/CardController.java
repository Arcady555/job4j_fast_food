package ru.job4j.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.order.model.Card;
import ru.job4j.order.service.CardService;

@AllArgsConstructor
@RestController
public class CardController {
    private CardService cardService;

    @PostMapping("/buy-card")
    public ResponseEntity<String> buyCard(Card card) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
