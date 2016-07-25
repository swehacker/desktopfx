/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Patrik Falk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.swehacker.desktopfx.openhab;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents an OpenHAB item.
 */
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private String name;
    private String state;
    private String link;
    private String label;
    @JsonIgnore
    private String stateDescription;
    @JsonIgnore
    private String category;
    @JsonIgnore
    private ArrayList members;
    @JsonIgnore
    private ArrayList groupNames;
    @JsonIgnore
    private ArrayList groupType;
    @JsonIgnore
    private ArrayList tags;

    Item() {}

    Item(final String type, final String name, final String label, final String state, final String link) {
        this.type = type;
        this.name = name;
        this.link = link;
        this.state = state;
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getLink() {
        return link;
    }

    public String getLabel() {
        return label;
    }


    public ArrayList getMembers() {
        return members;
    }

    public ArrayList getGroupNames() {
        return groupNames;
    }

    public ArrayList getTags() {
        return tags;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList getGroupType() {
        return groupType;
    }

    public String getStateDescription() {
        return stateDescription;
    }
}
