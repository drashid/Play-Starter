import net.liftweb.json._
import JsonDSL._
import RequireJsKeys._

seq(requireJsSettings: _*)

buildProfile in (Compile, requireJs) := (
  ("appDir" -> ".") ~
  ("baseUrl" -> "public/js") ~
  ("dir" -> "public/prod-resources") ~
  ("modules" -> List[JValue](("name" -> "admin-main")))
)

baseUrl in (Compile, requireJs) := "js"

mainConfigFile in (Compile, requireJs) <<=
  (sourceDirectory in (Compile, requireJs), baseUrl in (Compile, requireJs))((a, b) => Some(a / b / "admin-main.js"))