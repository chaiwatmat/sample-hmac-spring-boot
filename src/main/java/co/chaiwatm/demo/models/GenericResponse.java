package co.chaiwatm.demo.models;

import java.util.List;
import lombok.*;

@Data
public class GenericResponse<T> {
    private String responseUid;
    private T data;
    private List<ErrorResponse> errors;
}