package com.eclipse.panel.viewController;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ViewController implements ServletContextListener {

    public static final String FILEPATH = System.getProperty( "catalina.base" ) +"/team_eclipse/ROOT/";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
