package com.swehacker.hacfx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This is the configuration for a specific accessory.
 **/
public class Accessory extends AbstractEntity {

    public enum Type {
        SWITCH,
        LAMP,
        TEMPERATURE,
        HUMIDITY
    }

    private String openhab_id;
    private String label;
    private Type type;
    private String topic;
    private StringProperty value = new SimpleStringProperty("");

    /**
     * Returns the name of this accessory in OpenHAB.
     *
     * @return Returns the name of this accessory in OpenHAB
     */
    public String getOpenHABId() {
        return openhab_id;
    }

    /**
     * Sets the identifier for OpenHAB accessory
     *
     * @param name
     */
    public void setOpenHABId(String name) {
        this.openhab_id = name;
    }

    /**
     * Returns the name of this accessory that should describe this accessory e.g Desklamp or 3D Printer
     *
     * @return Returns the name of this accessory that should describe this accessory e.g Desklamp or 3D Printer
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the name you want to display on the client like Desklamp or 3D Printer.
     *
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the type of this accessory to help render correct icon or display the correct instrument.
     *
     * @return Returns the type of this accessory to help render correct icon or display the correct instrument
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Sets the type for instrumentation, e.g Lamp to display an lamp icon that you can turn on/off.
     *
     * @param type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Returns a MQTT topic channel where this accessory will publish it's changes
     * @return Returns a MQTT topic channel where this accessory will publish it's changes
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Topic is the MQTT channel where this accessory will send it's changes when updated.
     * The format used are /house/room/accessory, e.g.
     *     /apartment/bedroom/temperature
     * or
     *     /apartment/livingroom/desklamp
     *
     * @param topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getValue() {
        return this.value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}
