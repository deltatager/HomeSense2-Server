package com.slongpre.homesense.devices;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;

@Entity
public class RgbLight extends Light {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="brightness",column=@Column(name="redBrightness")),
            })
    private PwmLight red;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="brightness",column=@Column(name="greenBrightness")),
    })
    private PwmLight green;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="brightness",column=@Column(name="blueBrightness")),
    })
    private PwmLight blue;

    public RgbLight() {
        red = new PwmLight();
        green = new PwmLight();
        blue = new PwmLight();
    }

    public int getRed() {
        return red.getBrightness();
    }

    public int getGreen() {
        return green.getBrightness();
    }

    public int getBlue() {
        return blue.getBrightness();
    }

    public void setRed(int brightness) {
        red.setBrightness(brightness);
    }

    public void setGreen(int brightness) {
        green.setBrightness(brightness);
    }

    public void setBlue(int brightness) {
        blue.setBrightness(brightness);
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = Json.createObjectBuilder(super.toJson())
                .add("red", red.getBrightness())
                .add("green", green.getBrightness())
                .add("blue", blue.getBrightness())
                .build();
        return object;
    }

    @Override
    public String toString() {
        return super.toString() + ":" + red.getBrightness() + ":" + green.getBrightness() + ":" + blue.getBrightness();
    }
}
