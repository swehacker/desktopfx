package com.swehacker.hacfx.server.legend;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RepositoryRestResource
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findByTopic(String topic);
}
