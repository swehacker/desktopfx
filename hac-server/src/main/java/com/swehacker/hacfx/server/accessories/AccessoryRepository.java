package com.swehacker.hacfx.server.accessories;

import com.swehacker.hacfx.server.model.Accessory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface AccessoryRepository extends CrudRepository<Accessory, Long> {

}
