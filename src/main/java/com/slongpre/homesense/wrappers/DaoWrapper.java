package com.slongpre.homesense.wrappers;

import com.slongpre.homesense.entities.Device;
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

    private static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();

    }

    public static boolean isInvalidId(Integer id) {
        final Device result;
        try (Session session = getSession()) {
            final Query query = session.createQuery("from Device where id=" + id);
            result = (Device) query.uniqueResult();
        }
        return result == null;
    }

    public static String getAllDevices(String type) {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try (Session session = getSession()) {
            final Query query = session.createQuery("from " + type);
            for (Object o : query.list())
                builder.add(((Device) o).toJson());
        }
        return builder.build().toString();
    }

    public static String getAllDevices() {
        return getAllDevices("Device");
    }

    public static String getDeviceById(Integer id) {
        if (isInvalidId(id))
            return "";

        final String result;
        try (Session session = getSession()) {
            result = session.load(Device.class, id).toJson().toString();
        }
        return result;
    }

    public static Object update(Object o) {
        try (Session session = getSession()) {
            Transaction t = session.beginTransaction();
            o = (Device) session.merge(o);
            t.commit();
        }
        return o;
    }



}
