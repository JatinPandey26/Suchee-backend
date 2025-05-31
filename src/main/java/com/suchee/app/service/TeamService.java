package com.suchee.app.service;

import com.suchee.app.dto.TeamCreationDTO;
import com.suchee.app.dto.TeamDTO;
import com.suchee.app.dto.UserDTO;
import com.suchee.app.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService {

    public TeamDTO createTeam(TeamCreationDTO teamCreationDTO);

    public List<TeamDTO> getMyTeams();

    public TeamDTO editTeam(TeamDTO newTeam);

    public boolean deleteTeam(long id);

    List<TeamDTO> getTeamsByUserId(long userId);

    // only TEAM_ADMIN / ADMIN can do this
    boolean changeUserRoleForTeam(Long teamId, Long userId, RoleType role);

    Page<TeamDTO> getTeams(String searchKeyword, Pageable pageable);

    Page<TeamDTO> getTeams(Pageable pageable);

    Page<TeamDTO> getMyTeams(String searchKeyWord , Pageable pageable);

    Page<TeamDTO> getMyTeams(Pageable pageable);

    List<UserDTO> getTeamMembers(long teamId);

    boolean removeMemberFromTeam(long teamId, long userId);


    // below addMember apis will work with Invitation mechanism

    String addMemberToTeam(long teamId, String email);

    boolean addMemberToTeam(long teamId, long userId,RoleType roleType);

}
