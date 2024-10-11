package com.pj.dto;

public class PurchaseResponseDTO {
    private boolean allowed;
    private String message;

    // Constructors
    public PurchaseResponseDTO() {}

    public PurchaseResponseDTO(boolean allowed, String message) {
        this.allowed = allowed;
        this.message = message;
    }

    // Getters and Setters

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}