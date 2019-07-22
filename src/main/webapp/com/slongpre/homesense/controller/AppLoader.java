package com.slongpre.homesense.controller;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class AppLoader extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(DeviceController.class);
        h.add(LightController.class);
        h.add(RgbLightController.class);
        h.add(PwmLightController.class);
        return h;
    }
}