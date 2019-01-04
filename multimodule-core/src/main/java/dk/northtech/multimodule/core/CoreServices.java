package dk.northtech.multimodule.core;

import com.google.common.util.concurrent.AbstractIdleService;

public class CoreServices extends AbstractIdleService {
  public DataService dataService;

  @Override
  protected void startUp() throws Exception {
    this.dataService = GuiceModule.INJECTOR.getInstance(DataService.class);
  }

  @Override
  protected void shutDown() throws Exception {
  }
}
