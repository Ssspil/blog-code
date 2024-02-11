package aoxx.validation.domain.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ErrorResult {

    private Integer code;
    private String message;
    private Map<String, Object> data;


    public ErrorResult(Integer code, String message, Map<String, Object> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
