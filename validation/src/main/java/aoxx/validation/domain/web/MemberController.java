package aoxx.validation.domain.web;

import aoxx.validation.domain.member.dto.MemberRequestDto;
import aoxx.validation.domain.member.dto.MemberResponseDto;
import aoxx.validation.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by rius0918@gmail.com on 2024. 02. 11.
 * Blog : http://coasis.tistory.com
 * Github : http://github.com/ssspil
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 저장
    @PostMapping("/member")
    public MemberResponseDto join(@Validated @RequestBody MemberRequestDto memberRequestDto){
        return memberService.save(memberRequestDto);
    }

    // 회원 목록
    @GetMapping("/members")
    public List<MemberResponseDto> findAll(){
        return memberService.findAll();
    }
}
