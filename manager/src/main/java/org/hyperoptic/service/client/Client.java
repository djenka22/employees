package org.hyperoptic.service.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Client<R> {
    Long findIdByName(String name);
    R create(R entity);
    Page<R> findAll(Pageable pageable);
    R update(R entity);
    <B> void delete(B identifier);
}
