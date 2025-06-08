package com.suchee.app.repository;

import com.suchee.app.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryHelper {
    Page<Team> findTeamsByUserId(long userId, String keyword, Pageable pageable);
}
