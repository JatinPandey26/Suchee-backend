package com.suchee.app.endpoints;

import com.suchee.app.dto.BasicMessageResponseDto;
import com.suchee.app.dto.TeamCreationDTO;
import com.suchee.app.dto.TeamDTO;
import com.suchee.app.service.TeamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TeamDTO> createTeam(@ModelAttribute @Valid TeamCreationDTO teamCreationDTO){

        TeamDTO teamDTO = this.teamService.createTeam(teamCreationDTO);

        return ResponseEntity.ok(teamDTO);
    }

    @PatchMapping(value="/edit",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TeamDTO> edit(@ModelAttribute @Valid TeamDTO newTeam){

        TeamDTO teamDTO = this.teamService.editTeam(newTeam);

        return ResponseEntity.ok(teamDTO);
    }

    @PostMapping("/{teamId}/addMember")
    public ResponseEntity<BasicMessageResponseDto> addMember(@PathVariable long teamId , @Email @RequestParam String email){
        String response = this.teamService.addMemberToTeam(teamId,email);

        return ResponseEntity.ok(new BasicMessageResponseDto(response));
    }

}
