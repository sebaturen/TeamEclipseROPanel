package com.eclipse.panel.viewController;

import com.eclipse.panel.MVPSheetMonitoring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ViewController implements ServletContextListener {

    public static final String FILEPATH = System.getProperty( "catalina.base" ) +"/team_eclipse/ROOT/";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // Attribute
        //GuildController.getInstance().loadActivities(10);
        //sce.getServletContext().setAttribute("guild", GuildController.getInstance());

        // Prepare Schedule
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();

        // MVPSheet Monitoring
        schedule.scheduleAtFixedRate(new MVPSheetMonitoring(), 0, 1, TimeUnit.MINUTES);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
