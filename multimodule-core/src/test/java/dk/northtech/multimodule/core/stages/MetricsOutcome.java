package dk.northtech.multimodule.core.stages;

import com.codahale.metrics.MetricRegistry;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.truth.Truth.assertThat;

// The horrendous generics notation allows subclasses to return strongly typed instances of their own class.
public class MetricsOutcome<SELF extends MetricsOutcome<SELF>> extends Stage<SELF> {
  private static final Logger LOGGER = LoggerFactory.getLogger(MetricsOutcome.class);

  @ProvidedScenarioState
  MetricRegistry metricRegistry;

  public SELF a_MetricRegistry() {
    this.metricRegistry = new MetricRegistry();
    return self();
  }

  public SELF a_timer_named_$_has_been_logged_in_the_metrics(String timerName) {
    assertThat(this.metricRegistry.getTimers()).containsKey(timerName);
    return self();
  }
}
