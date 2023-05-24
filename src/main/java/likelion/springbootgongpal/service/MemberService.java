package likelion.springbootgongpal.service;

import likelion.springbootgongpal.domain.Member;

import java.util.List;

/*
 * 전달받은 데이터를 활용하여 트랜잭션 단위로 비즈니스 로직을 수행한다. 레포지토리를 이용하여 데이터를 사용/처리/관리함.
 * */

public interface MemberService {
    public void save(Member member);

    public Member findById(Long id);
    public List<Member> findAll();
}
