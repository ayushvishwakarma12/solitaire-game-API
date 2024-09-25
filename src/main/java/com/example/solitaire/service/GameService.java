package com.example.solitaire.service;

import com.example.solitaire.model.Card;
import com.example.solitaire.model.Deck;
import com.example.solitaire.model.GameState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;



public class GameService {
    private GameState gameState;


    public GameService() {
        this.gameState = new GameState();
        initializeGame();
    }

    private void initializeGame() {
        Deck deck = new Deck();
        deck.shuffleDeck();

        gameState.setPiles(new LinkedList<>());

        for (int i = 0; i < 10; i++) {
            gameState.getPiles().add(new LinkedList<>());
        }

        for (int i = 0; i < 10; i++) {
            int cardToDeal = (i < 4) ? 6 : 5;
            for (int j = 0; j < cardToDeal; j++) {
                if (!deck.getCards().isEmpty()) {

                    Card card = deck.getCards().remove(deck.getCards().size() - 1);
                    LoggerFactory.getLogger(GameService.class).info("card {}", card);
                    gameState.getPiles().get(i).add(card);
                }
            }
            LinkedList<Card> pile  = gameState.getPiles().get(i);
            if (!pile.isEmpty()) {
                pile.getLast().setFaceUp(true);
            }
        }
        gameState.setDeck(new LinkedList<>(deck.getCards()));
        LoggerFactory.getLogger(GameService.class).info("Draw pile size: {}", gameState.getDeck().size());
    }

    public void flipCard(int pileIndex, int cardIndex) {
        if (pileIndex < 0 || pileIndex >= gameState.getPiles().size()) {
            throw new IllegalArgumentException("Invalid pile index");
        }

        LinkedList<Card> pile = gameState.getPiles().get(pileIndex);
        if (cardIndex < 0 || cardIndex >= pile.size()) {
            throw new IllegalArgumentException("Invalid card index");
        }

        Card card = pile.get(cardIndex);
        card.flip();
        LoggerFactory.getLogger(GameService.class).info("Flipped card: {}", card);
    }

    public void moveCard(int sourcePileIndex, int sourceCardIndex, int targetPileIndex) {
        if (sourcePileIndex < 0 || sourcePileIndex >= gameState.getPiles().size()) {
            throw new IllegalArgumentException("Invalid source pile index");
        }

        LinkedList<Card> sourcePile = gameState.getPiles().get(sourcePileIndex);
        if (sourceCardIndex < 0 || sourceCardIndex >= sourcePile.size()) {
            throw new IllegalArgumentException("Invalid source card index");
        }

        Card cardToMove = sourcePile.get(sourceCardIndex); // Remove the card from the source pile

        if (targetPileIndex < 0 || targetPileIndex >= gameState.getPiles().size()) {
            throw new IllegalArgumentException("Invalid target pile index");
        }

        LinkedList<Card> targetPile = gameState.getPiles().get(targetPileIndex);


        if (!isValidMove(cardToMove, targetPile)) {
            throw new IllegalArgumentException("Invalid move");
        }

        sourcePile.remove(sourceCardIndex);
        targetPile.add(cardToMove);
        // Add the card to the target pile
        // Optionally, log the action
        LoggerFactory.getLogger(GameService.class).info("Moved card: {} from pile {} to pile {}", cardToMove, sourcePileIndex, targetPileIndex);
    }


    public GameState getGameState() {
        return gameState;
    }

    private boolean isValidMove(Card cardToMove, LinkedList<Card> targetPile) {
        // Implement Solitaire move rules here. For example:
        // - Check if target pile is empty (can only place a King)
        // - Check if the card can be placed on the target pile (color/rank rules)

        // Example: assuming targetPile is empty
        if (targetPile.isEmpty()) {
            return cardToMove.getValue() == 13; // Only Kings can be placed on empty piles
        }
        return  true;

    }




}
