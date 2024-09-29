package com.example.solitaire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    // Constructor to create a deck of 104 spade cards
    public Deck() {
        cards = new ArrayList<>();
        createDeck();
    }

    // Method to create 104 spade cards (8 sets of Ace to King)
    private void createDeck() {
        // Each value (Ace=1 to King=13) will appear 8 times
        for (int i = 0; i < 8; i++) { // Eight sets of spades
            for (int value = 1; value <= 13; value++) { // Card values from Ace to King
                cards.add(new Card(value));
            }
        }

    }

    // Method to get the deck of cards
    public List<Card> getCards() {
        return cards;
    }

    // Shuffle the deck of cards
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffleDeck();  //Shuffle the deck before displaying
        // Display all the cards in the deck
        for (Card card : deck.getCards()) {
            System.out.println(card);
        }

        System.out.println("Total number of cards: " + deck.getCards().size()); // Should output 104
    }

}
