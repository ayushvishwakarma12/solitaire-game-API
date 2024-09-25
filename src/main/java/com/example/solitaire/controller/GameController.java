package com.example.solitaire.controller;

import com.example.solitaire.model.Deck;
import com.example.solitaire.model.GameState;
import com.example.solitaire.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GameController {


    private final GameService gameService;

    @Autowired
    public GameController() {
        this.gameService = new GameService();
    }

    @PostMapping("/game/start-game")
    public String startGame() {
        gameService.getGameState();
        return "New game started!";
    }

    @GetMapping("/game/game-state")
    public GameState getGameState() {
        return gameService.getGameState();
    }

    @PostMapping("/flipCard")
    public ResponseEntity<GameState> flipCard(@RequestParam int pileIndex, @RequestParam int cardIndex) {
        gameService.flipCard(pileIndex, cardIndex);
        return ResponseEntity.ok(gameService.getGameState());
    }

    @PostMapping("/moveCard")
    public ResponseEntity<GameState> moveCard(@RequestParam int sourcePileIndex,
                                              @RequestParam int sourceCardIndex,
                                              @RequestParam int targetPileIndex) {
        gameService.moveCard(sourcePileIndex, sourceCardIndex, targetPileIndex);
        return ResponseEntity.ok(gameService.getGameState());
    }


}
