import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "ProductExplorer"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.google.inject" % "guice" % "3.0",
      "com.yammer.metrics" % "metrics-core" % "2.1.2",
      "com.yammer.metrics" % "metrics-guice" % "2.1.2"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
