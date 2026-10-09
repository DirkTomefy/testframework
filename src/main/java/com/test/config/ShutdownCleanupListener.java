package com.test.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class ShutdownCleanupListener implements ServletContextListener {

    private static final Logger LOG =
            Logger.getLogger(ShutdownCleanupListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("[cleanup] ShutdownCleanupListener enregistré");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        // Désenregistre tous les drivers JDBC chargés par le ClassLoader de la webapp
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == cl) {
                try {
                    DriverManager.deregisterDriver(driver);
                    LOG.info("[cleanup] Driver désenregistré : " + driver);
                } catch (SQLException e) {
                    LOG.log(Level.WARNING,
                            "[cleanup] Échec désenregistrement : " + driver, e);
                }
            }
        }

        LOG.info("[cleanup] Terminé");
    }
}