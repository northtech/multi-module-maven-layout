package dk.northtech.multimodule.core;

import com.google.common.util.concurrent.AbstractIdleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class CoreService extends AbstractIdleService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CoreService.class);

  @Override
  protected void startUp() throws Exception {
    LOGGER.info("DataService initialized");
  }

  public String performBackendService() {
    LOGGER.debug("Core DataService doing work");
    return "Yay!";
  }

  @Override
  protected void shutDown() throws Exception {
  }
}