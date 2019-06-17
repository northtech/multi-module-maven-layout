package dk.northtech.multimodule.webapi;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dk.northtech.multimodule.core.CoreService;
import dk.northtech.multimodule.webapi.metrics.MetricsGuiceModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This class is responsible for orchestrating the required injection system and controlling the lifecycle of
 * the services.
 */
@WebListener
public class ApplicationResources implements ServletContextListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationResources.class);
  public static CoreService coreService;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LOGGER.info("Application context initializing");
    Injector injector = Guice.createInjector(new MetricsGuiceModule());
    coreService = injector.getInstance(CoreService.class);
    coreService.startAsync().awaitRunning();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    if (coreService != null) {
      coreService.stopAsync().awaitTerminated();
    }
    ShutdownLogic.shutDownGeoTools();
    ShutdownLogic.deregisterDrivers();
    ShutdownLogic.shutdownLogback();
    ShutdownLogic.shutDownRxJava();
    // Defensively release all resources, to mitigate the impact of any misbehaving code causing a classloader leak:
    coreService = null;
  }
}
