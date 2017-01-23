package com.swehacker.hacfx.server.legend;

import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @NotNull @DateTimeFormat
    private long time;
    @NotNull @NotEmpty
    private String topic;
    private double value;

    public Event() {}

    public Event(Event event) {
        this.setTime(new Date().getTime());
        this.setTopic(event.getTopic());
        this.setValue(event.getValue());
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
