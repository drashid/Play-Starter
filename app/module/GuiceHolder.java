package module;



import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceHolder {

  private static final Injector inject = Guice.createInjector(new ApplicationModule());

  public static Injector injector() {
    return inject;
  }

}
