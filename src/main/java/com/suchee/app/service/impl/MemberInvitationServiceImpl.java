package com.suchee.app.service.impl;

import com.suchee.app.dto.MemberInvitationDto;
import com.suchee.app.dto.TeamDTO;
import com.suchee.app.entity.MemberInvitation;
import com.suchee.app.entity.Team;
import com.suchee.app.enums.MemberInvitationStatus;
import com.suchee.app.exception.ResourceNotFoundException;
import com.suchee.app.logging.Trace;
import com.suchee.app.mapper.MemberInvitationMapper;
import com.suchee.app.mapper.TeamMapper;
import com.suchee.app.messaging.async.AsyncEventPublishType;
import com.suchee.app.messaging.async.AsyncEventPublisher;
import com.suchee.app.messaging.async.impl.MemberInvitationCreatedEvent;
import com.suchee.app.repository.MemberInvitationRepository;
import com.suchee.app.repository.MemberRepository;
import com.suchee.app.service.MemberInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberInvitationServiceImpl implements MemberInvitationService {

    private final MemberRepository memberRepository;
    private final MemberInvitationRepository memberInvitationRepository;
    private final MemberInvitationMapper memberInvitationMapper;
    private final AsyncEventPublisher asyncEventPublisher;
    private final TeamMapper teamMapper;

    @Override
    public boolean createMemberInvitation(MemberInvitationDto memberInvitationDto) {
        MemberInvitation memberInvitation = this.memberInvitationMapper.toEntity(memberInvitationDto);

        MemberInvitation savedMemberInvite = this.memberInvitationRepository.save(memberInvitation);

        MemberInvitationDto savedMemberInvitationDto = this.memberInvitationMapper.toDto(savedMemberInvite);
        // create Message for queue
        publishInvitationMessage(savedMemberInvitationDto);
        return true;
    }

    @Override
    public boolean createMemberInvitation(TeamDTO teamDto, String email) {

        try{
            MemberInvitation memberAddInvitation = new MemberInvitation();
            memberAddInvitation.setEmail(email);

            Team team = this.teamMapper.toEntity(teamDto);

            memberAddInvitation.setTeam(team);

            MemberInvitation savedMemberInvite = this.memberInvitationRepository.save(memberAddInvitation);

            MemberInvitationDto memberInvitationDto = this.memberInvitationMapper.toDto(savedMemberInvite);
            // create Message for queue

            publishInvitationMessage(memberInvitationDto);
        }
        catch (Exception exception){
            exception.printStackTrace();
            Trace.error(exception.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public void changeStatusOfInvitation(long invitationId, MemberInvitationStatus status) {

        Optional<MemberInvitation> optionalMemberInvitation = this.memberInvitationRepository.findById(invitationId);

        if(optionalMemberInvitation.isEmpty()){
            throw new ResourceNotFoundException(MemberInvitation.getEntityName(),invitationId);
        }

        MemberInvitation memberInvitation = optionalMemberInvitation.get();

        memberInvitation.setStatus(status);

        this.memberInvitationRepository.save(memberInvitation);

    }

    public void publishInvitationMessage(MemberInvitationDto memberInvitationDto){
        MemberInvitationCreatedEvent memberInvitationCreatedEvent = new MemberInvitationCreatedEvent(memberInvitationDto, AsyncEventPublishType.QUEUE_EVENT);
        this.asyncEventPublisher.publish(memberInvitationCreatedEvent);
    }
}
