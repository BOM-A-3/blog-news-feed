package com.bom.newsfeed.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bom.newsfeed.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByNickname(String nickname);
}
