package co.chaiwatm.demo.models;

import lombok.*;

@Data
public class GenericResponse<T> {
    private String responseUid;
    private T data;
}