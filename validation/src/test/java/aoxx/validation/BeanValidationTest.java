package aoxx.validation;


import aoxx.validation.domain.member.dto.MemberRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class BeanValidationTest {

    @Test
    @DisplayName("검증이 잘 되는가?")
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        MemberRequestDto member = new MemberRequestDto();
        member.setEmail(" ");
        member.setName("  ");
        member.setPhoneNumber("0100101");

        Set<ConstraintViolation<MemberRequestDto>> violations = validator.validate(member);
        for (ConstraintViolation<MemberRequestDto> violation : violations) {
            System.out.println("violation : " + violation);
            System.out.println("violation.message : " + violation.getMessage());

        }
    }
}
