package com.suchee.app.repository;

import com.suchee.app.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team,Long> {
    Page<Team> findByNameContainingIgnoreCase(String teamName, Pageable pageable);
}
