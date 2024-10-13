package app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageDto {
    private Object result;
    private Date timestamp;

    public MessageDto(Object result) {
        this.timestamp = new Date();
        this.result = result;
    }
}
