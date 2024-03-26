package aoxx.validation.domain.member.service;

import aoxx.validation.domain.member.Member;
import aoxx.validation.domain.member.dto.MemberRequestDto;
import aoxx.validation.domain.member.dto.MemberResponseDto;
import aoxx.validation.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rius0918@gmail.com on 2024. 2. 11.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 저장
    public MemberResponseDto save(MemberRequestDto memberRequestDto){
        Member member = new Member();
        member.setName(memberRequestDto.getName());
        member.setEmail(memberRequestDto.getEmail());
        member.setPhoneNumber(memberRequestDto.getPhoneNumber());

        Member saveMember = memberRepository.save(member);

        return new MemberResponseDto(saveMember);
    }

    // 회원목록
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }
}
