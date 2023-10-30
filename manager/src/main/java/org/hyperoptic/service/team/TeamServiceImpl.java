package org.hyperoptic.service.team;

import org.hyperoptic.infrastructure.exceptions.custom.DataNotFoundException;
import org.hyperoptic.model.entity.Team;
import org.hyperoptic.model.team.TeamDtoBasic;
import org.hyperoptic.model.team.TeamDtoResponseCreate;
import org.hyperoptic.service.client.TeamClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class TeamServiceImpl implements TeamService {

    private final TeamClient teamClient;

    public TeamServiceImpl(TeamClient teamClient) {
        this.teamClient = teamClient;
    }

    @Override
    public <D> TeamDtoResponseCreate create(D dto) {
        TeamDtoBasic teamDtoBasic = (TeamDtoBasic) dto;
        Team team = Team.builder().withName(teamDtoBasic.getName()).build();
        Team createdTeam = teamClient.create(team);
        return new TeamDtoResponseCreate(createdTeam.getName(), "team has been successfully created");
    }

    @Override
    public List<TeamDtoBasic> findAll(Pageable pageable) {
        Page<Team> teams = teamClient.findAll(pageable);
        if (teams.isEmpty()) {
            throw new DataNotFoundException("No teams");
        }
        List<TeamDtoBasic> teamDtoBasicList = new ArrayList<>();
        for (Team team : teams) {
            teamDtoBasicList.add(new TeamDtoBasic(team.getName()));
        }
        return teamDtoBasicList;
    }

    @Override
    public <D, B> TeamDtoBasic update(D dto, B identifier) {
        TeamDtoBasic teamDtoBasic = (TeamDtoBasic) dto;
        String name = (String) identifier;
        Team team = teamClient.findByName(name).orElseThrow(
                () -> new DataNotFoundException("No team with name: " + name)
        );
        team.setName(teamDtoBasic.getName());
        Team updatedTeam = teamClient.update(team);
        return new TeamDtoBasic(updatedTeam.getName());
    }

    @Override
    @Transactional
    public <B> void delete(B identifier) {
        String name = (String) identifier;

        if (!teamClient.existByName(name))
            throw new DataNotFoundException("No team with name: " + name);

        teamClient.delete(name);
    }
}
