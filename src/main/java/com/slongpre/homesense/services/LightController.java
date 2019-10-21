package com.slongpre.homesense.services;

import com.slongpre.homesense.wrappers.DaoWrapper;
import com.slongpre.homesense.wrappers.NetworkWrapper;
import com.slongpre.homesense.entities.Light;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/lights")
public class LightController {

    @GET
    @Produces("application/json")
    public Response getAllLights() {
        return Response
                .status(Response.Status.OK)
                .entity(DaoWrapper.getAllDevices("Light"))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces(MediaType.TEXT_PLAIN)
    public Response setDeviceMode(@PathParam("id") Integer id, Light light) {
        if (DaoWrapper.isInvalidId(id))
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("")
                    .build();
        Light updatedLight = (Light) DaoWrapper.update(light);
        NetworkWrapper nw = new NetworkWrapper();
        try {
            nw.startConnection();
            nw.sendMessage(updatedLight.toJson());
            nw.stopConnection();
            System.out.println();
        } catch (IOException e) {
            System.err.println("You done goofed: " + e.getMessage());
        }

        return Response
                .status(Response.Status.OK)
                .entity(light.toJson().toString())
                .build();
    }
}
