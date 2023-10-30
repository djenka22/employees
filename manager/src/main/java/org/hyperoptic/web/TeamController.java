package org.hyperoptic.web;

import org.hyperoptic.model.team.TeamDtoBasic;
import org.hyperoptic.model.team.TeamDtoResponseCreate;
import org.hyperoptic.service.team.TeamService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/teams")

public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<TeamDtoBasic>> findAll(Pageable pageable) {
        List<TeamDtoBasic> teams = teamService.findAll(pageable);
        return ResponseEntity.ok(teams);
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<TeamDtoResponseCreate> createEmployee(
            @RequestBody @Valid TeamDtoBasic teamDtoBasic) {

        TeamDtoResponseCreate teamResponse = teamService.create(teamDtoBasic);
        return ResponseEntity.ok(teamResponse);
    }

    @RequestMapping(value = "/update/{name}", method = RequestMethod.PUT)
    public ResponseEntity<TeamDtoBasic> updateEmployee(
            @RequestBody @Valid TeamDtoBasic teamDtoBasic,
            @NotNull @PathVariable String name) {

        TeamDtoBasic teamResponse = teamService.update(teamDtoBasic, name);
        return ResponseEntity.ok(teamResponse);
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTeam(
            @NotNull @PathVariable String name) {

        teamService.delete(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
