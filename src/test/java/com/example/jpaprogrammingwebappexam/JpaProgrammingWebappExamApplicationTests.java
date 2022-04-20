package com.example.jpaprogrammingwebappexam;

import com.example.jpaprogrammingwebappexam.persistence.domain.Member;
import com.example.jpaprogrammingwebappexam.repository.ItemRepository;
import com.example.jpaprogrammingwebappexam.repository.MemberRepository;
import com.example.jpaprogrammingwebappexam.service.MemberService;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JpaProgrammingWebappExamApplicationTests extends TestTemplate {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ItemRepository itemRepository;

	@Order(1) @Test
//	@Rollback(false) // 테스트 시에는 각 메소드 실행마다 강제로 rollback 처리를 해서 순서로 진행하는 테스트에 방해된다.
	void memberJoinTest() {
		_(() -> {
			Member member = new Member();
			member.setName("kim");

			Long id = memberService.join(member);
			assertEquals(member, memberRepository.findOne(id));
		});
	}

	@Order(2) @Test
	void memberFindTest() {
		_(() -> {
			Member member = new Member();
			member.setName("fuck");
			memberService.join(member);

			List<Member> members = memberRepository.findAll();
			assert(members.size()>0);
		});
	}

	@Order(3) @Test
	void memberDuplicateTest() {
		_(() -> {
			Member member1 = new Member();
			Member member2 = new Member();
			member1.setName("kim");
			member2.setName("kim");

			assertThrows(IllegalStateException.class, () -> {
				memberService.join(member1);
				memberService.join(member2);
			});
		});
	}


}
