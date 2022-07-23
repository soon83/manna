package com.sss.infrastructure.member;

import com.sss.domain.member.interest.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberInterestRepository extends JpaRepository<Interest, Long> {
    Optional<Interest> findByToken(String interestToken);
}
