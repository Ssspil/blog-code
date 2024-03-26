package aoxx.validation.domain.member.dto;


import aoxx.validation.domain.member.Member;
import lombok.*;

/**
 * Created by rius0918@gmail.com on 2024. 2. 11.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;

    public MemberResponseDto(Member member) {
        id = member.getId();
        name = member.getName();
        phoneNumber = member.getPhoneNumber();
        email = member.getEmail();
    }
}
