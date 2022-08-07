package com.sss.infrastructure.member;

import com.sss.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryQuerydsl {
    Optional<Member> findByEmail(String memberEmail);
    Optional<Member> findByToken(String memberToken);
}
