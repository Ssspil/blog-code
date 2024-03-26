package aoxx.validation.domain.member.repository;


import aoxx.validation.domain.member.Member;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rius0918@gmail.com on 2024. 2. 11.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
@Repository
public class MemberRepository {
    private Map<Long, Member> store = new HashMap<>();
    private Long sequence = 1L;


    @PostConstruct
    public void init(){

        Member member1 = new Member(sequence++, "김길동", "01012345678", "example1@tt.com");
        Member member2 = new Member(sequence++, "이천수", "01055665678", "example2@tt.com");
        Member member3 = new Member(sequence++, "조규성", "01012342234", "example3@tt.com");

        store.put(member1.getId(), member1);
        store.put(member2.getId(), member2);
        store.put(member3.getId(), member3);
    }

    public Member save(Member member){
        member.setId(sequence++);
        store.put(member.getId(), member);
        return member;
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

}
