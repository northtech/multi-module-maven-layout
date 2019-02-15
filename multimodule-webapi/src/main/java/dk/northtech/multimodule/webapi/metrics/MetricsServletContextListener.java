package dk.northtech.multimodule.webapi.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

import javax.servlet.annotation.WebListener;

@WebListener
public class MetricsServletContextListener extends MetricsServlet.ContextListener {
  static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

  @Override
  protected MetricRegistry getMetricRegistry() {
    return METRIC_REGISTRY;
  }
}
