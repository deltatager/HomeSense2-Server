package com.slongpre.homesense.controller;

import com.slongpre.homesense.dataManagement.DaoWrapper;
import com.slongpre.homesense.devices.Device;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    @GET
    @Path("/supported")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupportedTypes() {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.slongpre.homesense.devices"))));

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (Class c : classes) {
            builder.add(c.getSimpleName());
        }

        return Response
                .status(Response.Status.OK)
                .entity(builder.build().toString())
                .build();
    }

    @PUT
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewDevice(Device newDevice) {

        Device returned = DaoWrapper.update(newDevice);

        return Response
                .status(Response.Status.CREATED)
                .entity(returned.toJson().toString())
                .build();
    }
}