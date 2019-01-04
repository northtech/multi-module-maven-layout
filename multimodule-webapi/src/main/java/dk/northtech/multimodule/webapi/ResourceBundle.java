package dk.northtech.multimodule.webapi;

import dk.northtech.multimodule.core.CoreServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ResourceBundle implements ServletContextListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBundle.class);
  public static CoreServices coreServices;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LOGGER.info("Application context initializing");
    coreServices = new CoreServices();
    coreServices.startAsync().awaitRunning();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    if (coreServices != null) {
      coreServices.stopAsync().awaitTerminated();
    }
  }
}
