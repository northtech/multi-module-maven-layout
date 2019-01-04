package dk.northtech.multimodule.webapi;

import com.google.common.collect.ImmutableSet;
import dk.northtech.gsonextensions.jaxrsgsongeo.GeoMessageBodyHandler;
import dk.northtech.gsonextensions.jaxrsgsongeo.GeoParamConverterProvider;
import dk.northtech.gsonextensions.jaxrssupport.JaxRsHttpStatus;
import dk.northtech.gsonextensions.jaxrssupport.Rfc7159StringMessageBodyHandler;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/")
public class WebApplication extends Application {
  @Override
  public Set<Class<?>> getClasses() {
    return ImmutableSet.of(
      // Convert POST and return values:
      GeoMessageBodyHandler.class,
      // Allow simple (quoted) JSON strings in addition to objects:
      Rfc7159StringMessageBodyHandler.class,
      // Convert @QueryParam("...") input parameters:
      GeoParamConverterProvider.class,
      // Filter to tag a returned object with a status code:
      JaxRsHttpStatus.class,

      // Register the actual REST interfaces:
      SomeServiceWebApi.class
    );
  }
}
