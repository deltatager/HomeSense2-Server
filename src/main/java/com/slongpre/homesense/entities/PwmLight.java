package com.slongpre.homesense.entities;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
@Embeddable
public class PwmLight extends Light {

    private int brightness;

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness > 255 ? 255 : brightness;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = Json.createObjectBuilder(super.toJson())
                .add("brightness", brightness)
                .build();
        return object;
    }

    @Override
    public String toString() {
        return super.toString() + ":" + brightness;
    }
}
