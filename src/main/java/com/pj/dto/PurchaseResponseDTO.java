package com.pj.dto;

/**
 * 购买响应 DTO
 */
public class PurchaseResponseDTO {
    private boolean allowed;
    private String message;
    private String code; // 提示码

    // Constructors
    public PurchaseResponseDTO() {}

    public PurchaseResponseDTO(boolean allowed, String message, String code) {
        this.allowed = allowed;
        this.message = message;
        this.code = code;
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

    public void setMessage(String message){
        this.message = message;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }
}