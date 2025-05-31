package com.suchee.app.service.impl;

import com.suchee.app.dto.*;
import com.suchee.app.entity.*;
import com.suchee.app.enums.MemberStatus;
import com.suchee.app.enums.RoleType;
import com.suchee.app.exception.ResourceAlreadyExistsException;
import com.suchee.app.exception.ResourceNotFoundException;
import com.suchee.app.logging.Trace;
import com.suchee.app.mapper.TeamMapper;
import com.suchee.app.mapper.UserAccountMapper;
import com.suchee.app.repository.MemberRepository;
import com.suchee.app.repository.TeamRepository;
import com.suchee.app.security.SecurityContext;
import com.suchee.app.service.*;
import com.suchee.app.utils.StorageInfoBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final AttachmentService attachmentService;
    private final MemberRepository memberRepository;
    private final MemberInvitationService memberInvitationService;
    private final RoleService roleService;
    private final UserAccountMapper userAccountMapper;
    private final MemberService memberService;


    @Override
    public TeamDTO createTeam(TeamCreationDTO teamCreationDTO) {

        // AuthRule
        // Only Authenticated users

        if(Trace.team){
            Trace.log("Team creation started");
            Trace.debug(teamCreationDTO);
        }

        Team team = this.teamMapper.toEntity(teamCreationDTO);
        Team savedTeam = this.teamRepository.save(team);

        // create new Member for current user with role TEAM_ADMIN

        MemberCreateDTO member = new MemberCreateDTO();
        UserAccount currentUser = SecurityContext.getCurrentUserAccount();
        member.setUser(this.userAccountMapper.toDto(currentUser));

        RoleDTO role = this.roleService.findByRoleType(RoleType.TEAM_ADMIN);

        member.setRoles(Set.of(role));
        member.setTeamId(savedTeam.getId());
        member.setStatus(MemberStatus.ACTIVE);

        this.memberService.createMember(member);

        // process Avatar

        if (teamCreationDTO.getAvatar() != null && !teamCreationDTO.getAvatar().isEmpty()) {
            AttachmentStorageInfoDto attachmentStorageInfo = StorageInfoBuilder.teamAttachmentPath(String.valueOf(savedTeam.getId()),teamCreationDTO.getAvatar().getOriginalFilename());
            Attachment avatar = this.attachmentService.process(teamCreationDTO.getAvatar(), attachmentStorageInfo);

            savedTeam.setAvatar(avatar);
            savedTeam = this.teamRepository.save(savedTeam);
        }




        TeamDTO teamDTO = this.teamMapper.toDto(savedTeam);

        if(Trace.team){
            Trace.log("Team created with Id : " + teamDTO.getId() + " , Name : " + teamDTO.getTeamName());
        }

        return teamDTO;
    }

    @Override
    public List<TeamDTO> getMyTeams() {
        return List.of();
    }

    @Override
    public TeamDTO editTeam(TeamDTO newTeam) {

        // AuthRule
        // Only TeamAdmin and belong to this team

        if (Trace.team) {
            Trace.log("Team edit started");
            Trace.debug("Incoming update:", newTeam);
        }

        Optional<Team> existingTeamOpt = teamRepository.findById(newTeam.getId());
        if (existingTeamOpt.isEmpty()) {
            throw new ResourceNotFoundException(Team.getEntityName(),newTeam.getId());
        }

        Team originalTeam = existingTeamOpt.get();

        this.teamMapper.updateTeamFromDto(newTeam,originalTeam);

        Team updatedTeam = this.teamRepository.save(originalTeam);

        TeamDTO teamDTO = this.teamMapper.toDto(updatedTeam);

        if (Trace.team) {
            Trace.log("Team updated with Id : " + teamDTO.getId() + " , Name : " + teamDTO.getTeamName());
        }

        return teamDTO;
    }


    @Override
    public boolean deleteTeam(long id) {

        // AuthRule
        // Only TeamAdmin

        // TODO: Work on archive feature

        if (Trace.team) {
            Trace.log("Team delete requested for Id: " + id);
        }

        try {
            this.teamRepository.deleteById(id);

            if (Trace.team) {
                Trace.log("Team deleted successfully for Id: " + id);
            }

            return true;
        } catch (Exception e) {
            if (Trace.team) {
                Trace.log("Failed to delete team with Id: " + id);
                Trace.debug("Exception:", e.getMessage());
            }
            return false;
        }
    }


    @Override
    public List<TeamDTO> getTeamsByUserId(long userId) {
        return List.of();
    }

    @Override
    public boolean changeUserRoleForTeam(Long teamId, Long userId, RoleType role) {
        return false;
    }

    @Override
    public Page<TeamDTO> getTeams(String searchKeyword, Pageable pageable) {

        // AuthRule
        // TeamAdmin only

        if (Trace.team) {
            Trace.log("Fetching teams...");
            Trace.debug("Search Keyword:", searchKeyword);
            Trace.debug("Pageable:", pageable);
        }

        Page<Team> teamPage;

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            teamPage = teamRepository.findByTeamNameContainingIgnoreCase(searchKeyword, pageable);
        } else {
            teamPage = teamRepository.findAll(pageable);
        }

        Page<TeamDTO> result = teamPage.map(this.teamMapper::toDto);

        if (Trace.team) {
            Trace.log("Fetched " + result.getNumberOfElements() + " teams on page " + result.getNumber());
        }

        return result;
    }


    @Override
    public Page<TeamDTO> getTeams(Pageable pageable) {
        return getTeams("",pageable);
    }

    @Override
    public Page<TeamDTO> getMyTeams(String searchKeyWord, Pageable pageable) {

        // get team in which current user is a member

        return null;
    }

    @Override
    public Page<TeamDTO> getMyTeams(Pageable pageable) {
        return null;
    }

    @Override
    public List<UserDTO> getTeamMembers(long teamId) {
        return List.of();
    }

    @Override
    public boolean removeMemberFromTeam(long teamId, long userId) {
        return false;
    }

    @Override
    public String addMemberToTeam(long teamId, String email) {

        // only TEAM_ADMIN

        Optional<Team> optionalTeam = this.teamRepository.findById(teamId);

        if(optionalTeam.isEmpty()){
            throw new ResourceNotFoundException(Team.getEntityName(),teamId);
        }

        // check if already a member
        Optional<Member> optionalMember = this.memberRepository.findByTeam(optionalTeam.get());

        if(optionalMember.isPresent()){
            Trace.error("Member with email : " + email + " already exists in team with teamId : " + teamId);
            throw new ResourceAlreadyExistsException("Member with email : " + email + " already exists in team" );
        }

        // send Invite to member
        this.memberInvitationService.createMemberInvitation(this.teamMapper.toDto(optionalTeam.get()),email);

        return "Member Invited to team successfully";
    }

    @Override
    public boolean addMemberToTeam(long teamId, long userId, RoleType roleType) {
        return false;
    }
}
