package dk.northtech.multimodule.webapi.serviceapis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import static dk.northtech.multimodule.webapi.ApplicationResources.coreService;

@Path("someservice")
public class SomeServiceWebApi {
  @GET
  public String performService() {
    return coreService.performBackendService();
  }
}
