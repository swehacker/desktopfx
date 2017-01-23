package com.swehacker.hacfx.server.legend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RepositoryRestResource
public interface EventRepository extends CrudRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.time >= ?1")
    List<Event> findAll(long time);

    @Query("SELECT e from Event e where e.topic = ?1 and e.time >= ?2")
    List<Event> findByTopic(String topic, long time);
}
