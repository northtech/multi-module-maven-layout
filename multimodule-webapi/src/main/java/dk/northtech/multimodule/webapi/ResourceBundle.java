package dk.northtech.multimodule.webapi;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dk.northtech.multimodule.core.CoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ResourceBundle implements ServletContextListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBundle.class);
  public static CoreService coreService;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LOGGER.info("Application context initializing");
    Injector injector = Guice.createInjector();
    coreService = injector.getInstance(CoreService.class);
    coreService.startAsync().awaitRunning();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    if (coreService != null) {
      coreService.stopAsync().awaitTerminated();
    }
  }
}