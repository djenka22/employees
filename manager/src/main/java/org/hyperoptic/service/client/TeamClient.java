package org.hyperoptic.service.client;

import org.hyperoptic.model.entity.Team;
import org.hyperoptic.repository.TeamRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class TeamClient implements Client<Team>{
    private final TeamRepository teamRepository;

    public TeamClient(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    @Override
    public Long findIdByName(String name) {
        return teamRepository.findIdByName(name);
    }

    public String findNameById(Long id) {return teamRepository.findNameById(id);}

    @Override
    public Team create(Team entity) {
        return teamRepository.save(entity);
    }

    @Override
    public Page<Team> findAll(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    @Override
    public Team update(Team entity) {
        return teamRepository.save(entity);
    }

    @Override
    public <B> void delete(B identifier) {
        String name = (String) identifier;
        teamRepository.deleteByName(name);

    }

    public Optional<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }

    public boolean existByName(String name) {
        return teamRepository.existsByName(name);
    }
}
