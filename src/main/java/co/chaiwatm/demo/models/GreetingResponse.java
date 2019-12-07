package co.chaiwatm.demo.models;

import lombok.*;

@Data
public class GreetingResponse {
    private String responseUid;
    private User user;
}