package com.example.solitaire.controller;
import com.example.solitaire.model.GameState;
import com.example.solitaire.model.MoveCardRequest;
import com.example.solitaire.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;


@RestController
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = new GameService();
    }

    @PostMapping("/game/start-game")
    public void startGame() {
        gameService.initializeGame();
    }

    @GetMapping("/game/game-state")
    public GameState getGameState() {
        return gameService.getGameState();
    }

    @PostMapping("/game/deck/distribute")
    public ResponseEntity<GameState> distributeCards() {
        gameService.distributeCards();
        return ResponseEntity.ok(gameService.getGameState());
    }

    @MessageMapping("/game/moveCard")
    @SendTo("/topic/gameState")
    public GameState moveCard(MoveCardRequest request) {
        gameService.moveCard(request.getSourcePileIndex(), request.getSourceCardIndex(), request.getTargetPileIndex());
        return gameService.getGameState();
    }

}
