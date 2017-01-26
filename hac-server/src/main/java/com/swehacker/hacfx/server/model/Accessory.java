package com.swehacker.hacfx.server.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This is the configuration for a specific accessory.
 * The original plan was to create an accessory that was connected to a room that was connected to a specific
 * house/apartment. But that will only over complicate the model (since it's just my apartment and it will only control
 * my house). It might be prudent in the future to split up these to different tables and reduce duplication of text.
 */
@Entity
@Table(name = "accessories")
public class Accessory extends AbstractEntity {

    @NotNull
    private String name;
    @NotNull
    private String label;
    @NotNull
    private String topic;
    @NotNull
    private String room;
    @NotNull
    private String house;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
