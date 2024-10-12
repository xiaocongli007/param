package com.pj.model;

import javax.persistence.*;

@Entity
@Table(name = "response_messages")
public class ResponseMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="code", unique=true, nullable=false)
    private String code;

    @Column(name="message", nullable=false)
    private String message;

    // Constructors
    public ResponseMessage() {}

    public ResponseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}