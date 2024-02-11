package aoxx.validation.domain.member;

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
public class Member {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;

}
