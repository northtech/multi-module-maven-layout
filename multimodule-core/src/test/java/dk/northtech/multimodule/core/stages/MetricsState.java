package dk.northtech.multimodule.core.stages;

import com.codahale.metrics.MetricRegistry;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

// The horrendous generics notation allows subclasses to return strongly typed instances of their own class.
public class MetricsState<SELF extends MetricsState<SELF>> extends Stage<SELF> {
  @ProvidedScenarioState
  MetricRegistry metricRegistry;

  public SELF a_MetricRegistry() {
    this.metricRegistry = new MetricRegistry();
    return self();
  }
}
