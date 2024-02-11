package aoxx.validation.domain.member.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by rius0918@gmail.com on 2024. 2. 11.
 * Blog : http://aoxx.co.kr
 * Github : https://github.com/Ssspil
 */

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberRequestDto {

    private Long id;
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotBlank
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다.")
    private String phoneNumber;

    @NotBlank
    @Email(message = "이메일 양식을 지켜주세요")
    private String email;

}
