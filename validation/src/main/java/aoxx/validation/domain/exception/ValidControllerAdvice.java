package aoxx.validation.domain.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ValidControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult MethodArgumentNotValidExHandler(MethodArgumentNotValidException e) {
        Map<String, Object> data = new HashMap<>();

        List<Map<String, Object>> list = new ArrayList<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            Map<String, Object> field = new HashMap<>();
            field.put("field", fieldError.getField());
            field.put("msg", fieldError.getDefaultMessage());
            field.put("rejectedValue", fieldError.getRejectedValue());

            list.add(field);
        }
        data.put("connStatus", "fail");
        data.put("errorList", list);

        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), "실패", data);
    }
}
