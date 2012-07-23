import sbt._
import Keys._
import PlayProject._

import sbt.Defaults._
import sbt.Package.ManifestAttributes

object ApplicationBuild extends Build {

  val appName         = "ProductExplorer"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.google.inject" % "guice" % "3.0",
    "com.yammer.metrics" % "metrics-core" % "2.1.2",
    "com.yammer.metrics" % "metrics-guice" % "2.1.2",
    "redis.clients" % "jedis" % "2.1.0"
  )

  val requireOptimizeTask = TaskKey[Unit]("optimizejs")

  //to avoid multiple optimization runs - will need to figure out why this is needed for sbt
  var done = false

  val requireJsSettings = requireOptimizeTask := {  
    if(System.getenv("DISABLE_REQUIREJS_OPT") == null && !done){
      done = true
      println("To disable this for DEVLEOPMENT set the environment variable: DISABLE_REQUIREJS_OPT")
      "java -classpath project/js.jar org.mozilla.javascript.tools.shell.Main project/r.js -o project/app.build.js" !
    }else{
      println("RequireJS optimization disabled.")
    }
  }

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    requireJsSettings, 
    (compile in Compile) <<= (compile in Compile).dependsOn(requireOptimizeTask)
  )

}