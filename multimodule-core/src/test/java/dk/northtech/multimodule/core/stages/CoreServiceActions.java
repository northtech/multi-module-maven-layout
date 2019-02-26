package dk.northtech.multimodule.core.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import dk.northtech.multimodule.core.CoreService;

public class CoreServiceActions extends Stage<CoreServiceActions> {
  @ExpectedScenarioState
  CoreService coreService;

  @ProvidedScenarioState
  String serviceResult;

  public CoreServiceActions backend_service_is_called() {
    this.serviceResult = this.coreService.performBackendService();
    return self();
  }
}
