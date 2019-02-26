package dk.northtech.multimodule.core.stages;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import dk.northtech.multimodule.core.CoreService;

import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CoreServiceState extends MetricsState<CoreServiceState> {
  @ProvidedScenarioState
  CoreService coreService;

  public CoreServiceState a_configured_CoreService() {
    this.coreService = new CoreService(this.metricRegistry);
    try {
      this.coreService.startAsync().awaitRunning(30, SECONDS);
    } catch (TimeoutException e) {
      throw new RuntimeException(e);
    }
    return self();
  }
}
