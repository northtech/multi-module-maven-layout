This package hooks up the DropWizard Metrics by providing central (singleton) Metric/Health registries exposed to
Metrics' `AdminServlet` through the dedicated `ContextListener`s (as per
https://metrics.dropwizard.io/4.0.0/manual/servlets.html),
as well as exposed to the dependency injection system through the `MetricsGuiceModule`.  