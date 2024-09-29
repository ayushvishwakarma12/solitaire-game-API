package com.example.solitaire.model;

import java.util.LinkedList;
import java.util.List;

public class GameState {
    private List<LinkedList<Card>> piles;
    private LinkedList<Card> deck;
    private List<LinkedList<Card>> foundation;
    private int score;
    private int moves;
    private int completedSequence;


    public GameState() {
        this.piles = new LinkedList<>();
        this.foundation = new LinkedList<>();
        this.deck = new LinkedList<>();
        this.score = 0;
        this.moves = 0;
        this.completedSequence = 0;
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

    public int getCompletedSequence() {
        return completedSequence;
    }

    public void setCompletedSequence(int completedSequence) {
        this.completedSequence = completedSequence;
    }

}
