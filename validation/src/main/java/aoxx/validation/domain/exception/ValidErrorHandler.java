package aoxx.validation.domain.exception;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult MethodArgumentNotValidExHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();

        Map<String, Object> data = new HashMap<>();
        data.put("connStatus" , "fail");
        data.put("Errors" , globalErrors);

        return new ErrorResult(400, e.getMessage(), data);
    }

}
