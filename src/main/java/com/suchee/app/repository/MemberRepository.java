package com.suchee.app.repository;

import com.suchee.app.entity.Member;
import com.suchee.app.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByTeam(Team team);

    @Query("""
    SELECT m.team FROM Member m 
    WHERE m.user.id = :userId 
    AND (:search IS NULL OR LOWER(m.team.name) LIKE LOWER(CONCAT('%', :search, '%')))
""")
    Page<Team> findTeamsByUserId(@Param("userId") long userId , @Param("search") String searchKeyword , Pageable pageable);
}
