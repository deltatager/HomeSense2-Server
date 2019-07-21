package com.slongpre.homesense.devices;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;

@Entity
public class Light extends Device {

    protected LightMode mode;

    public LightMode getMode() {
        return mode;
    }

    public void setMode(LightMode mode) {
        this.mode = mode;
    }

    public enum LightMode {
        ON, OFF;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = Json.createObjectBuilder(super.toJson())
                .add("mode", mode.toString())
                .build();
        return object;
    }

    @Override
    public String toString() {
        return super.toString() + ":" + mode;
    }

}
