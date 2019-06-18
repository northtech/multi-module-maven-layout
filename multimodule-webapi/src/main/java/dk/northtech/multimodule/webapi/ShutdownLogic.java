package dk.northtech.multimodule.webapi;

import ch.qos.logback.classic.LoggerContext;
import io.reactivex.schedulers.Schedulers;
import org.geotools.data.DataAccessFinder;
import org.geotools.data.DataStoreFinder;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.geotools.referencing.factory.AbstractAuthorityFactory;
import org.geotools.referencing.factory.DeferredAuthorityFactory;
import org.geotools.referencing.wkt.Formattable;
import org.geotools.util.WeakCollectionCleaner;
import org.opengis.referencing.AuthorityFactory;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains shutdown routines for various tools and libraries. Notably, getting rid of all GeoTools resources
 * for a clean shutdown in a webapp is not trivial.
 * Projects may not need all these (though if you have the classes/libraries so the corresponding shutdown logic even
 * compiles, odds are you need the shutdown).
 */
public class ShutdownLogic {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownLogic.class);

  /**
   * This can be combined with a
   * <pre>&lt;context-param&gt;<br>  &lt;param-name&gt;logbackDisableServletContainerInitializer&lt;/param-name&gt;<br>  &lt;param-value&gt;true&lt;/param-value&gt;<br>&lt;/context-param&gt;</pre>
   * in the <code>web.xml</code> to prevent Logback from installing the overly aggressive
   * ServletContainerInitializer which shuts down the logging context before the webapp's
   * ServletContextListener#contextDestroyed runs, preventing logging during shutdown.
   */
  public static void shutdownLogback() {
    // Check if we own a Logback logger context, and if so, shut it down manually:
    ILoggerFactory context = LoggerFactory.getILoggerFactory();
    if (context instanceof LoggerContext && context.getClass().getClassLoader() == ShutdownLogic.class.getClassLoader()) {
      LOGGER.debug("Shutting down logging context. Over and out.");
      ((LoggerContext) context).stop();
    }
  }

  public static void shutDownRxJava() {
    Schedulers.shutdown();
  }

  public static void shutDownGeoTools() {
    // Unload all deferred authority factories so we get rid of the timer tasks in them
    // Disposing of the HSQLDB EPSG database raises an "illegal reflective access" warning. GeoTools maintainers are
    // aware of this, with Andrea Aime raising an issue with HSQLDB: https://sourceforge.net/p/hsqldb/bugs/1526/
    disposeAuthorityFactories(ReferencingFactoryFinder.getCoordinateOperationAuthorityFactories(null));
    disposeAuthorityFactories(ReferencingFactoryFinder.getCRSAuthorityFactories(null));
    disposeAuthorityFactories(ReferencingFactoryFinder.getCSAuthorityFactories(null));

    WeakCollectionCleaner.DEFAULT.exit();
    DeferredAuthorityFactory.exit();
    CRS.reset("all");

    ReferencingFactoryFinder.reset();
    CommonFactoryFinder.reset();
    DataStoreFinder.reset();
    DataAccessFinder.reset();
    CRS.cleanupThreadLocals();
    Formattable.cleanupThreadLocals();
    Introspector.flushCaches();
  }

  private static void disposeAuthorityFactories(Set<? extends AuthorityFactory> factories) {
    for (AuthorityFactory af : factories) {
      if (af instanceof AbstractAuthorityFactory) {
        try {
          ((AbstractAuthorityFactory) af).dispose();
          LOGGER.debug("Disposed referencing factory {}", af.getClass().getCanonicalName());
        } catch (Exception e) {
          LOGGER.warn("Error occurred while trying to dispose CRSAuthorityFactories", e);
        }
      }
    }
  }

  /**
   * JDBC drivers typically auto-register using SPI simply by being in the classpath,
   * but they cannot similarly auto-deregister. This method finds the drivers which are loaded in the
   * current classloader (i.e. web-app) and deregisters them.
   */
  public static void deregisterDrivers() {
    Set<Driver> registeredDrivers = new HashSet<>();
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    while (drivers.hasMoreElements()) {
      registeredDrivers.add(drivers.nextElement());
    }
    ClassLoader localClassLoader = ShutdownLogic.class.getClassLoader();
    for (Driver driver : registeredDrivers) {
      if (driver.getClass().getClassLoader() == localClassLoader) {
        try {
          DriverManager.deregisterDriver(driver);
          LOGGER.debug("Deregistering JDBC driver {}", driver);
        } catch (SQLException ex) {
          LOGGER.warn("Error deregistering JDBC driver {}", driver, ex);
        }
      }
    }
  }
}
