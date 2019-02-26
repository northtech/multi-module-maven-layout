package dk.northtech.multimodule.core;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.util.concurrent.AbstractIdleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * The service is implemented as a singleton, using Guava's Service interface, for a standardized instantiation and
 * initialization/lifecycle.
 */
@Singleton
public class CoreService extends AbstractIdleService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CoreService.class);
  private final Timer timer;

  @Inject
  public CoreService(MetricRegistry metricRegistry) {
    this.timer = metricRegistry.timer(name(CoreService.class, "core-service-time"));
  }

  @Override
  protected void startUp() throws Exception {
    LOGGER.info("CoreService initialized");
  }

  public String performBackendService() {
    try (var timerContext = timer.time()) {
      LOGGER.debug("CoreService doing work");
      return "Yay!";
    }
  }

  @Override
  protected void shutDown() throws Exception {
  }
}
