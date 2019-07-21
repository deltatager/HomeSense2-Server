package com.slongpre.homesense.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/devices")
public class DeviceController {

    @GET
    @Produces("application/json")
    public Response getAllDevices() {
        return Response
                .status(Response.Status.OK)
                .entity(DaoWrapper.getAllDevices())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getDeviceById(@PathParam("id") Integer id) {
        String content = DaoWrapper.getDeviceById(id);

        return Response
                .status(content.isEmpty() ? Response.Status.NOT_FOUND : Response.Status.OK)
                .entity(content)
                .build();
    }
}