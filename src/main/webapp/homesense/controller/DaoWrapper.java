package com.slongpre.homesense.controller;

import com.slongpre.homesense.devices.Device;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

public class DaoWrapper {

    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("/hibernate.cfg.xml");

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();

    }

    public static boolean isValidId(Integer id) {
        final Device result;
        final Session session = getSession();
        try {
            final Query query = session.createQuery("from Device where id=" + id);
            result = (Device) query.uniqueResult();
        } finally {
            session.close();
        }
        return result != null;
    }

    public static String getAllDevices(String type) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        final Session session = getSession();

        try {
            final Query query = session.createQuery("from " + type);
            for (Object o : query.list())
                builder.add(((Device) o).toJson().toString());
        } finally {
            session.close();
        }
        return builder.build().toString();
    }

    public static String getAllDevices() {
        return getAllDevices("Device");
    }

    public static Object getAllLights() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        final Session session = getSession();
        try {
            final Query query = session.createQuery("from Light");
            for (Object o : query.list())
                builder.add(((Device) o).toJson().toString());
        } finally {
            session.close();
        }
        return builder.build().toString();
    }

    public static String getDeviceById(Integer id) {
        if (!isValidId(id))
            return "";

        final String result;
        final Session session = getSession();
        try {
            result = session.load(Device.class, new Integer(id)).toJson().toString().toString();
        } finally {
            session.close();
        }
        return result;
    }

    public static Device update(Device device) {
        final Session session = getSession();
        try {
            Transaction t = session.beginTransaction();
            device = (Device) session.merge(device);
            t.commit();
        } finally {
            session.close();
        }
        return device;
    }


}
