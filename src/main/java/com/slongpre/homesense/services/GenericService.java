package com.slongpre.homesense.services;

import com.slongpre.homesense.entities.Device;
import com.slongpre.homesense.wrappers.DaoWrapper;
import com.slongpre.homesense.wrappers.Jsonable;
import com.slongpre.homesense.wrappers.NetworkWrapper;
import com.slongpre.homesense.entities.PwmLight;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/")
public class GenericService {

    @GET
    @Produces("application/json")
    public Response getAllDevices() {
        return Response
                .status(Response.Status.OK)
                .entity(DaoWrapper.getAllDevices())
                .build();
    }

        @PUT
        @Path("/{id}")
        @Consumes("application/json")
        @Produces(MediaType.TEXT_PLAIN)
        public Response setDeviceMode (@PathParam("id") Integer id, Device o){
            if (DaoWrapper.isInvalidId(id))
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("")
                        .build();
            Device updated = (Device) DaoWrapper.update(o);
            NetworkWrapper nw = new NetworkWrapper();
            try {
                nw.startConnection();
                nw.sendMessage(updated.toJson());
                nw.stopConnection();
            } catch (IOException e) {
                System.err.println("You done goofed: " + e.getMessage());
            }
            return Response
                    .status(Response.Status.OK)
                    .entity(o.toJson().toString())
                    .build();
        }
    }
