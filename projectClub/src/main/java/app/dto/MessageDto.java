package app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageDto {
    private Object result;
    private int statusCode;
    private Date timestamp;

    public MessageDto(Object result, int statusCode) {
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.result = result;
    }
}
