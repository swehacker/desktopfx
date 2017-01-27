package com.swehacker.hacfx.server.accessories;

import com.swehacker.hacfx.server.model.House;
import com.swehacker.hacfx.server.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface HouseRepository extends CrudRepository<House, Long> {
    @Query("SELECT r FROM Room r WHERE r.id = ?1")
    Room findRoom(Long roomId);
}
