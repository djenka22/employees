package org.hyperoptic.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService<T,R> {
    <D> R create(D dto);
    List<T> findAll(Pageable pageable);
    <D,B> T update(D dto, B identifier);
    <B> void delete (B identifier);
}
