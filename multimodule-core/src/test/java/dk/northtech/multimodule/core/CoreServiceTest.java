package dk.northtech.multimodule.core;

import com.tngtech.jgiven.junit5.ScenarioTest;
import dk.northtech.multimodule.core.stages.CoreServiceActions;
import dk.northtech.multimodule.core.stages.CoreServiceOutcome;
import dk.northtech.multimodule.core.stages.CoreServiceState;
import org.junit.jupiter.api.Test;

class CoreServiceTest extends ScenarioTest<CoreServiceState, CoreServiceActions, CoreServiceOutcome> {

  @Test
  void performBackendService() {
    given()
      .a_MetricRegistry()
      .and().a_configured_CoreService();
    when()
      .backend_service_is_called();
    then()
      .result_is_$("Yay!")
      .and().a_timer_named_$_has_been_logged_in_the_metrics("dk.northtech.multimodule.core.CoreService.core-service-time");
  }
}
