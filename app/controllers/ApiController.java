package controllers;

import static module.GuiceHolder.injector;
import play.mvc.Controller;
import play.mvc.Result;

public class ApiController extends Controller {

  private static final String API_PATH = "controllers.api";

  public static Result api(String path) throws Exception {
    // Find class name
    StringBuffer sb = capitalizeClassName(new StringBuffer(API_PATH).append(".")
        .append(path.replace('/', '.').toLowerCase()));

    try {
      ApiOperation op = (ApiOperation)injector().getInstance(Class.forName(sb.toString()));
      return op.call();
    } catch (Exception e) {
      return badRequest();
    }
  }

  private static StringBuffer capitalizeClassName(StringBuffer sb) {
    int index = sb.lastIndexOf(".");
    if (index >= 0) {
      char classNameStart = sb.charAt(index + 1);
      String upperCaseChar = String.valueOf(Character.toUpperCase(classNameStart));
      return sb.replace(index + 1, index + 2, upperCaseChar);
    }
    return sb;
  }

}
