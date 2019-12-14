package co.chaiwatm.demo.models;

import lombok.*;

@Data
public class ErrorResponse {
    private String code;
    private String message;
    private String level;
}