package com.swehacker.desktopfx.openhab;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class SensorValue {
    private String link;
    private String state;
    @JsonIgnore
    private List stateDescription;
    private String type;
    private String name;
    private String label;
    private String category;
    private List tags;
    private List groupNames;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(List stateDescription) {
        this.stateDescription = stateDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
    }

    public List getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(List groupNames) {
        this.groupNames = groupNames;
    }
}
