package dk.northtech.multimodule.webapi.metrics;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

import javax.servlet.annotation.WebListener;

@WebListener
public class HealthCheckServletContextListener extends HealthCheckServlet.ContextListener {
  static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();

  @Override
  protected HealthCheckRegistry getHealthCheckRegistry() {
    return HEALTH_CHECK_REGISTRY;
  }
}
