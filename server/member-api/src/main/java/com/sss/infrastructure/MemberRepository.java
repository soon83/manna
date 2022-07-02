package com.sss.infrastructure;

import com.sss.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String username);

    Optional<Member> findByToken(String memberToken);
}
