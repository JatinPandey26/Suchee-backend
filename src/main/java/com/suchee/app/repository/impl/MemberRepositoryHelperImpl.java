package com.suchee.app.repository.impl;

import com.suchee.app.entity.Team;
import com.suchee.app.repository.MemberRepositoryHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

public class MemberRepositoryHelperImpl implements MemberRepositoryHelper {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Page<Team> findTeamsByUserId(long userId, String keyword, Pageable pageable) {
       String getMyTeamsQuery = "SELECT tm.team FROM Team t left join Member tm on t.id = tm.team_id WHERE tm.user_id = :userId\n" +
               "AND (:search IS NULL OR LOWER(t.team_name) LIKE LOWER(CONCAT('%' , :search , '%')))";

        TypedQuery<Team> getMyTeamsTypedQuery = entityManager.createQuery(getMyTeamsQuery,Team.class);

        getMyTeamsTypedQuery.setParameter("userId",userId);
        if (StringUtils.hasText(keyword)) {
            getMyTeamsTypedQuery.setParameter("search",keyword);
        }

        getMyTeamsTypedQuery.setFirstResult((int) pageable.getOffset());
        getMyTeamsTypedQuery.setMaxResults(pageable.getPageSize());

        List<Team> teams = getMyTeamsTypedQuery.getResultList();

        TypedQuery<Long> getMyTeamsCountTypedQuery = entityManager.createQuery(getMyTeamsQuery,Long.class);

        getMyTeamsTypedQuery.setParameter("userId",userId);
        if (StringUtils.hasText(keyword)) {
            getMyTeamsTypedQuery.setParameter("search",keyword);
        }
        long total = getMyTeamsCountTypedQuery.getSingleResult();


        return new PageImpl<>(teams,pageable,total);

    }
}
