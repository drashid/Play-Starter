import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "ProductExplorer"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.google.inject" % "guice" % "3.0",
      "org.reflections" % "reflections" % "0.9.8"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
