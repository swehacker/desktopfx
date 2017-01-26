package com.swehacker.hacfx.server.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name="rooms")
public class Room extends AbstractEntity {
    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id")
    private Set<Accessory> accessories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
