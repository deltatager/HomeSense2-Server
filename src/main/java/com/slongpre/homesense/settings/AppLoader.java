package com.slongpre.homesense.settings;

import com.slongpre.homesense.controller.DeviceController;
import com.slongpre.homesense.controller.LightController;
import com.slongpre.homesense.controller.PwmLightController;
import com.slongpre.homesense.controller.RgbLightController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @Path("/")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response index() {
        return Response
                .status(Response.Status.OK)
                .entity("App running!")
                .build();
    }
}