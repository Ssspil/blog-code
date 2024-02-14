package aoxx.validation.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResult {

    private Integer code;
    private String message;
    private Map<String, Object> data;

}
