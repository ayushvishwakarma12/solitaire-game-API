package com.example.solitaire.model;

public class Card {
    private int value;
    private boolean isFaceUp;
    private String suit;
    private String imageUrl;

    public Card(int value) {
        this.value = value;
        this.isFaceUp = false;
        this.suit = "spades";
        this.imageUrl = generateImageUrl();
    }

    public int getValue() {
        return value;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void flip() {
        this.isFaceUp = !this.isFaceUp;
    }

    public void setFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    //Method to generate the URL for the card image
    private String generateImageUrl() {

        return "https://deckofcardsapi.com/static/img/" + getCardCode() + ".png";
    }

    private String getCardCode() {
        switch (value) {
            case 1: return "AS";
            case 2: return "2S";
            case 3: return "3S";
            case 4: return "4S";
            case 5: return "5S";
            case 6: return "6S";
            case 7: return "7S";
            case 8: return "8S";
            case 9: return "9S";
            case 10: return "0S";
            case 11: return "JS";
            case 12: return "QS";
            case 13: return "KS";
            default: throw new IllegalArgumentException("Invalid card value");
        }
    }


    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", isFaceUp=" + isFaceUp +
                ", suit='" + suit + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}
