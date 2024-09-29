package com.example.solitaire.model;

public class MoveCardRequest {
    private int sourcePileIndex;
    private int sourceCardIndex;
    private int targetPileIndex;


    public int getSourcePileIndex() {
        return sourcePileIndex;
    }

    public void setSourcePileIndex(int sourcePileIndex) {
        this.sourcePileIndex = sourcePileIndex;
    }

    public int getSourceCardIndex() {
        return this.sourceCardIndex;
    }

    public void setSourceCardIndex(int sourceCardIndex) {
        this.sourceCardIndex = sourceCardIndex;
    }

    public int getTargetPileIndex() {
        return this.targetPileIndex;
    }

    public void setTargetPileIndex(int targetPileIndex) {
        this.targetPileIndex = targetPileIndex;
    }
}
