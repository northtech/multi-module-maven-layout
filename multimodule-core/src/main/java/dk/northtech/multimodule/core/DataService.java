package dk.northtech.multimodule.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataService {
  private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);

  @Inject
  public DataService() {
    LOGGER.info("DataService initialized");
  }

  public String performBackendService() {
    LOGGER.debug("Core DataService doing work");
    return "Yay!";
  }
}
