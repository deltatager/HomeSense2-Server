package com.slongpre.homesense.entities;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected int id;
    protected int address;
    protected String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonObject toJson() {
        JsonObject object = Json.createObjectBuilder()
                .add("instanceOf", this.getClass().getSimpleName().toLowerCase())
                .add("id", id)
                .add("address", address)
                .add("name", name)
                .build();
        return object;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().toLowerCase() + name + ":" + id + ":" + address;
    }
}
