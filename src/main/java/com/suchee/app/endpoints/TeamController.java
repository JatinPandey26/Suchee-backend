package com.suchee.app.endpoints;

import com.suchee.app.dto.BasicMessageResponseDto;
import com.suchee.app.dto.TeamCreationDTO;
import com.suchee.app.dto.TeamDTO;
import com.suchee.app.service.TeamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    TeamService teamService;

    TeamController(TeamService teamService){
        this.teamService=teamService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<TeamDTO> createTeam(@RequestBody @Valid TeamCreationDTO teamCreationDTO){

        TeamDTO teamDTO = this.teamService.createTeam(teamCreationDTO);

        return ResponseEntity.ok(teamDTO);
    }

    @PatchMapping(value="/edit")
    public ResponseEntity<TeamDTO> edit(@RequestBody @Valid TeamDTO newTeam){

        TeamDTO teamDTO = this.teamService.editTeam(newTeam);

        return ResponseEntity.ok(teamDTO);
    }

    @PostMapping("/{teamId}/addMember")
    public ResponseEntity<BasicMessageResponseDto> addMember(@PathVariable long teamId , @Email @RequestParam String email){
        String response = this.teamService.addMemberToTeam(teamId,email);

        return ResponseEntity.ok(new BasicMessageResponseDto(response));
    }

    @GetMapping("/my")
    public ResponseEntity<Page<TeamDTO>> getMyTeams(  @RequestParam(required = false) String search,
                                                      @PageableDefault(size = 10) Pageable pageable){
        Page<TeamDTO> myTeams =  this.teamService.getMyTeams(search,pageable);
        return ResponseEntity.ok(myTeams);
    }

}
