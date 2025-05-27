package com.suchee.app.service.impl;

import com.suchee.app.dto.TeamCreationDTO;
import com.suchee.app.dto.TeamDTO;
import com.suchee.app.dto.UserDTO;
import com.suchee.app.entity.Attachment;
import com.suchee.app.entity.Team;
import com.suchee.app.enums.RoleType;
import com.suchee.app.exception.ResourceNotFoundException;
import com.suchee.app.logging.Trace;
import com.suchee.app.mapper.TeamMapper;
import com.suchee.app.repository.TeamRepository;
import com.suchee.app.service.AttachmentService;
import com.suchee.app.service.TeamService;
import com.suchee.app.utils.StoragePathBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private TeamMapper teamMapper;
    private AttachmentService attachmentService;

    TeamServiceImpl (TeamRepository teamRepository,TeamMapper teamMapper,AttachmentService attachmentService){
        this.teamRepository=teamRepository;
        this.teamMapper=teamMapper;
        this.attachmentService=attachmentService;
    }

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
        // process Avatar

        if (teamCreationDTO.getAvatar() != null && !teamCreationDTO.getAvatar().isEmpty()) {
            String attachmentPath = StoragePathBuilder.teamAvatarPath(String.valueOf(savedTeam.getId()));
            Attachment avatar = this.attachmentService.process(teamCreationDTO.getAvatar(), attachmentPath);

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
    public List<UserDTO> getTeamMembers(long teamId) {
        return List.of();
    }

    @Override
    public boolean removeMemberFromTeam(long teamId, long userId) {
        return false;
    }

    @Override
    public boolean addMemberToTeam(long teamId, long userId) {
        return false;
    }

    @Override
    public boolean addMemberToTeam(long teamId, long userId, RoleType roleType) {
        return false;
    }
}
