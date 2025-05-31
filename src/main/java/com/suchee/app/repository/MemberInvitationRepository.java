package com.suchee.app.repository;

import com.suchee.app.entity.MemberInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInvitationRepository extends JpaRepository<MemberInvitation,Long> {
}
