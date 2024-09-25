package com.example.solitaire.model;

import java.util.LinkedList;
import java.util.List;

public class GameState {
    private List<LinkedList<Card>> piles;
    private LinkedList<Card> deck;
    private List<LinkedList<Card>> foundation;
    private int score;
    private int moves;


    public GameState() {
        this.piles = new LinkedList<>();
        this.foundation = new LinkedList<>();
        this.deck = new LinkedList<>();
        this.score = 0;
        this.moves = 0;
    }

    public List<LinkedList<Card>> getPiles() {
        return piles;
    }

    public void setPiles(List<LinkedList<Card>> piles) {
        this.piles = piles;
    }

    public LinkedList<Card> getDeck() {
        return deck;
    }

    public void setDeck(LinkedList<Card> deck) {
        this.deck = deck;
    }

    public List<LinkedList<Card>> getFoundation() {
        return foundation;
    }

    public void setFoundation(List<LinkedList<Card>> foundation) {
        this.foundation = foundation;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    //Method to extract a valid sequence starting from the selected card
    public List<Card> extractSequence(LinkedList<Card> pile, int startIndex) {
        List<Card> sequence = new LinkedList<>();
        sequence.add(pile.get(startIndex));

        for (int i = startIndex + 1; i < pile.size(); i++) {
            Card current = pile.get(i);
            Card previous = pile.get(i - 1);

            if (previous.getValue() - current.getValue() == 1) {
                sequence.add(current);
            } else {
                break;
            }
        }
        return sequence;
    }

    //Method to remove the sequence from the original pile
    public void removeSequence(LinkedList<Card> pile, int startIndex) {
        while (pile.size() > startIndex) {
            pile.remove(startIndex);
        }
    }

    // Method to move a sequence from one pile to another
    public void moveSequence(LinkedList<Card> sourcePile, int startIndex, LinkedList<Card> destinationPile) {
        List<Card> sequence = extractSequence(sourcePile, startIndex);
        removeSequence(sourcePile, startIndex);
        destinationPile.addAll(sequence);
    }

}
