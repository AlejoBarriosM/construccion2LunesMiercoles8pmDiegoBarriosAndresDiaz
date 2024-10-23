package app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageDto {
    private Object body;
    private int statusCodeValue;
    private String statusCode;

    public MessageDto(Object body, int statusCodeValue, String statusCode) {
        this.body = body;
        this.statusCodeValue = statusCodeValue;
        this.statusCode = statusCode;
    }
}
