package com.example.solitaire.service;

import com.example.solitaire.model.Card;
import com.example.solitaire.model.Deck;
import com.example.solitaire.model.GameState;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
public class GameService {
    private GameState gameState;

    public GameService() {
        this.gameState = new GameState();
        initializeGame();
    }

    public void initializeGame() {
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
                    gameState.getPiles().get(i).add(card);
                }
            }
            LinkedList<Card> pile  = gameState.getPiles().get(i);
            if (!pile.isEmpty()) {
                pile.getLast().setFaceUp(true);
            }
        }
        for (Card card : deck.getCards()) {
            card.setFaceUp(true);
        }

        gameState.setDeck(new LinkedList<>(deck.getCards()));
        gameState.setMoves(0);
        LoggerFactory.getLogger(GameService.class).info("Draw pile size: {}", gameState.getDeck().size());
    }

    public void flipCard(int pileIndex) {
        LinkedList<Card> pile = gameState.getPiles().get(pileIndex);
        Card card = pile.getLast();
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

        // Get the sequence of cards to move
        List<Card> cardsToMove = sourcePile.subList(sourceCardIndex, sourcePile.size());
        LoggerFactory.getLogger(GameService.class).info("List length {} ", cardsToMove.size());
        if (cardsToMove.size() > 1) {
            if (!isValidSequenceMove(cardsToMove, targetPile)) {
                throw new IllegalArgumentException("Invalid move sequence");
            }
            targetPile.addAll(cardsToMove);
            sourcePile.subList(sourceCardIndex, sourcePile.size()).clear();
            gameState.setMoves(gameState.getMoves() + 1);
            if (!sourcePile.get(sourcePile.size() - 1).isFaceUp()) {
                flipCard(sourcePileIndex);
            }

        } else {
            if (!isValidMove(cardToMove, targetPile)) {
                throw new IllegalArgumentException("Invalid move");
            }
            sourcePile.remove(sourceCardIndex);
            targetPile.add(cardToMove);
            gameState.setMoves(gameState.getMoves() + 1);
            if (!sourcePile.get(sourcePile.size() - 1).isFaceUp()) {
                flipCard(sourcePileIndex);
            }
        }

        checkAndRemoveCompleteSequence();

        LoggerFactory.getLogger(GameService.class).info("Moved card: {} from pile {} to pile {}", cardToMove, sourcePileIndex, targetPileIndex);
    }

    public boolean isValidMove(Card cardToMove, LinkedList<Card> targetPile) {
        if (targetPile.isEmpty()) {
            return true;
        }

        Card topCard = targetPile.getLast();
        int valueDifference = cardToMove.getValue() - topCard.getValue();

        return valueDifference == -1;
    }

    public boolean isValidSequenceMove(List<Card> cardsToMove, LinkedList<Card> targetPile) {
        if (targetPile.isEmpty()) {
            return  true;
        }
        LoggerFactory.getLogger(GameService.class).info("Moved card: {} ", cardsToMove);
        Card topCard = targetPile.getLast();
        Card bottomCard = cardsToMove.get(0);
        LoggerFactory.getLogger(GameService.class).info("Last card: {} ", bottomCard);

        int valueDifference = bottomCard.getValue() - topCard.getValue();
        LoggerFactory.getLogger(GameService.class).info("Card value: {} ", valueDifference);

        if (valueDifference != -1) {
            return false;
        }

        for (int i = 0; i < cardsToMove.size() - 1; i++) {
            LoggerFactory.getLogger(GameService.class).info("Card value after difference: {} ", cardsToMove.get(i).getValue() - cardsToMove.get(i + 1).getValue());
            if (cardsToMove.get(i).getValue() - cardsToMove.get(i + 1).getValue() != 1) {
                return false;
            }
        }
        return  true;
    }


    public GameState getGameState() {
        return gameState;
    }

    private boolean isKingToAceSequence(List<Card> cards) {
        if (cards.size() < 13) {
            return false;
        }

        for (int i = 0; i < 13; i++) {
            if (cards.get(i).getValue() != (13 - i)) {
                return false;
            }
        }
        return true;
    }

    private void checkAndRemoveCompleteSequence() {
        for (int i = 0; i < gameState.getPiles().size(); i++) {
            LinkedList<Card> pile = gameState.getPiles().get(i);
            if (pile.size() >= 13) {
                List<Card> possibleSequence = pile.subList(pile.size() - 13, pile.size());
                if (isKingToAceSequence(possibleSequence)) {
                    // Remove the sequence
                    possibleSequence.clear();
                    gameState.setCompletedSequence(gameState.getCompletedSequence() + 1);

                    // Invoke flipCard to flip the last card after the sequence is removed
                    if (!pile.isEmpty()) {
                        flipCard(i);
                    }

                    LoggerFactory.getLogger(GameService.class).info("Removed a complete King-to-Ace sequence");
                }
            }
        }
    }

    public void distributeCards() {
        LinkedList<Card> deck = gameState.getDeck();

        if (deck.size() < 10) {
            throw new IllegalStateException("Not enough cards in the deck to distribute");
        }

        for (int i = 0; i < 10; i++) {
            LinkedList<Card> pile = gameState.getPiles().get(i);
            Card card = deck.removeLast();
            card.setFaceUp(true);
            pile.add(card);

        }
        gameState.setDeck(deck);
        LoggerFactory.getLogger(GameService.class).info("Distributed cards to piles");
    }
}
