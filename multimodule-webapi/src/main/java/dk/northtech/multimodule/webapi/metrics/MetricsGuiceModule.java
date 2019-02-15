package dk.northtech.multimodule.webapi.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.inject.AbstractModule;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MetricsGuiceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(MetricRegistry.class).toInstance(MetricsServletContextListener.METRIC_REGISTRY);
    bind(HealthCheckRegistry.class).toInstance(HealthCheckServletContextListener.HEALTH_CHECK_REGISTRY);
  }
}
