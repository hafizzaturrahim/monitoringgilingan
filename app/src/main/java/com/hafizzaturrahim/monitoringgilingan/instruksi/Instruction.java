package com.hafizzaturrahim.monitoringgilingan.instruksi;

/**
 * Created by PC-34 on 3/31/2017.
 */

public class Instruction {

    String titleInstruction;
    String senderInstriction;
    String recipientInstruction;
    String detailInstruction;
    String statusInsruction;

    public Instruction() {
    }

    public String getTitleInstruction() {
        return titleInstruction;
    }

    public void setTitleInstruction(String titleInstruction) {
        this.titleInstruction = titleInstruction;
    }

    public String getSenderInstriction() {
        return senderInstriction;
    }

    public void setSenderInstriction(String senderInstriction) {
        this.senderInstriction = senderInstriction;
    }

    public String getRecipientInstruction() {
        return recipientInstruction;
    }

    public void setRecipientInstruction(String recipientInstruction) {
        this.recipientInstruction = recipientInstruction;
    }

    public String getDetailInstruction() {
        return detailInstruction;
    }

    public void setDetailInstruction(String detailInstruction) {
        this.detailInstruction = detailInstruction;
    }

    public String getStatusInsruction() {
        return statusInsruction;
    }

    public void setStatusInsruction(String statusInsruction) {
        this.statusInsruction = statusInsruction;
    }
}
