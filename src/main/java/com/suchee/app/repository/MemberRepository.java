package com.suchee.app.repository;

import com.suchee.app.entity.Member;
import com.suchee.app.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByTeam(Team team);

}
