package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class) // 스프링에 관련하여 테스트할거야!
class MemberRepositoryTest {

    // !!! Entity의 관한 모든 데이터 이동은 트랜잭션을 통해야 한다

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional // Transactional Annotation이 Test클래스에 있으면 실행하고 다시 DB로 Rollback을 한다
    @Rollback(value = false) // Rollback 취소 (실제 값이 들어가게)
    public void testMember() throws Exception{

        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        // Assert는 인자로 전달된 A와 B가 동일한지 확인
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // 저장된 객체와 조회된 객체 비교 (true)
        // 같은 트랜잭션안에서 저장을 하고 조회를 하면 영속성 콘텍스트가 같다
        // 같은 영속성 콘텍스트 안에서 id값이 같으면 같은 entity값으로 인식
        Assertions.assertThat(findMember).isEqualTo(member); // == 비교

    }

}