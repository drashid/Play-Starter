

import module.ApplicationModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

class GuiceHolder {

  private static final Injector inject = Guice.createInjector(new ApplicationModule());

  public static Injector injector() {
    return inject;
  }

}
