package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository // component scan의 대상이 되서 자동으로 spring bean의 등록
public class MemberRepository {

    // https://gmlwjd9405.github.io/2019/08/06/persistence-context.html
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId(); // 커멘더와 쿼리를 분리하라
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
