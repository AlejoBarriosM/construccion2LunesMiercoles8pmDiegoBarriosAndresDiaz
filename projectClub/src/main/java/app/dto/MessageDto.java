package app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageDto {
    private Object message;
    private String status;
    private Date timestamp;

    public MessageDto(Object message, String status) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
    }
}
