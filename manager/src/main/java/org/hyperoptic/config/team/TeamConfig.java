package org.hyperoptic.config.team;

import org.hyperoptic.repository.TeamRepository;
import org.hyperoptic.service.client.TeamClient;
import org.hyperoptic.service.team.TeamService;
import org.hyperoptic.service.team.TeamServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamConfig {
    @Bean
    public TeamService teamService(TeamClient teamClient) {
        return new TeamServiceImpl(teamClient);
    }
    @Bean
    public TeamClient teamClient (TeamRepository teamRepository) {
        return new TeamClient(teamRepository);
    }
}
