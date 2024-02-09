package aoxx.validation.domain.web;

import aoxx.validation.domain.member.Member;
import aoxx.validation.domain.member.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rius0918@gmail.com on 2024. 02. 11.
 * Blog : http://aoxx.co.kr
 * Github : http://github.com/ssspil
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    @PostMapping("/member")
    public Member join(@Validated @RequestBody MemberRequestDto memberRequestDto){
        return new Member(memberRequestDto.getName(), memberRequestDto.getPhoneNumber(), memberRequestDto.getEmail());
    }
}
