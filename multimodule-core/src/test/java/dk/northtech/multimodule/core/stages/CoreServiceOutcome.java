package dk.northtech.multimodule.core.stages;

import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.truth.Truth.assertThat;

public class CoreServiceOutcome extends MetricsOutcome<CoreServiceOutcome> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CoreServiceOutcome.class);

  @ExpectedScenarioState
  String serviceResult;

  public CoreServiceOutcome result_is_$(String expectedResult) {
    assertThat(this.serviceResult).isEqualTo(expectedResult);
    return self();
  }
}
