import com.unhandledexpression.sbtclojure.ClojurePlugin.clojure._
import com.unhandledexpression.sbtclojure.ClojurePlugin._
import sbt._
import sbt.Keys._

import scala._

object BuildSettings {
  val buildVersion = "0.1"
  val buildScalaVersion = "2.11.4"
  val buildClojureVersion = "1.6.0"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    libraryDependencies ++= Dependencies.list,
    resolvers ++= Resolvers.list,
    testOptions in Test += Tests.Argument("-oD")
  )
//  val clojureSettings = Seq(
//    clojureVersion := "1.5.1"
//  )
}

object Resolvers {


  lazy val clojars = "Clojars" at "https://clojars.org/repo"

  lazy val list = List(clojars)
}

object Dependencies {

  lazy val scalaTest = "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

  lazy val clojureLib = "org.clojure" % "clojure" % "1.6.0"

//  lazy val clojureInClojure = "org.clojure" % "clojure" % "1.5.1" % clojure.Config.name

  lazy val clara = "org.toomuchcode" % "clara-rules" % "0.8.2"

  lazy val logger = Seq(
    "org.clapper" %% "grizzled-slf4j" % "1.0.2",
    "ch.qos.logback" % "logback-classic" % "1.1.2")

  def list = List(scalaTest, clojureLib, clara) ++ logger
}

object StashekBuild extends Build {

  import BuildSettings._
  val projectName = "stashek"

  lazy val project = Project(
    id = projectName,
    base = file("."),
    settings = buildSettings ++ Seq(name := projectName) ++ clojure.settings
  )
}
