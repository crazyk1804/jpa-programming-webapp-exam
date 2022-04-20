package com.example.jpaprogrammingwebappexam.service;

import com.example.jpaprogrammingwebappexam.persistence.domain.Member;
import com.example.jpaprogrammingwebappexam.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional // RuntimeException 하위의 예외만 롤백 처리됨
// @Transactional(Exception.class) 같은 방식으로 예외를 지정해 줄 수 있음
public class MemberService {
	@Autowired
	private MemberRepository repository;

	private void validateDuplicateMember(Member member) {
		List<Member> found = repository.findByName(member.getName());
		if(!found.isEmpty()) throw new IllegalStateException("이미 존재하는 회원입니다.");
	}

	public Long join(Member member) {
		validateDuplicateMember(member);
		repository.save(member);
		return member.getId();
	}

	public List<Member> findMembers() {
		return repository.findAll();
	}

	public Member findOne(Long memberId) {
		return repository.findOne(memberId);
	}
}
