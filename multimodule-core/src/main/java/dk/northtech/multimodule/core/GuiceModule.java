package dk.northtech.multimodule.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceModule extends AbstractModule {
  public static final Injector INJECTOR = Guice.createInjector(new GuiceModule());

  @Override
  protected void configure() {
  }
}
