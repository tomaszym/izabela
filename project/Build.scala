import com.unhandledexpression.sbtclojure.ClojurePlugin.clojure._
import com.unhandledexpression.sbtclojure.ClojurePlugin._
import sbt._
import sbt.Keys._
import play.PlayImport._

import scala._

object BuildSettings {
  val buildVersion = "0.1"
  val buildScalaVersion = "2.11.5"
  val buildClojureVersion = "1.6.0"

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    libraryDependencies ++= Dependencies.base,
    resolvers ++= Resolvers.list,
    testOptions in Test += Tests.Argument("-oD")
  )
//  val clojureSettings = Seq(
//    clojureVersion := "1.5.1"
//  )
}

object Resolvers {

  lazy val clojars = "Clojars" at "https://clojars.org/repo"
  lazy val scalaz = "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

  lazy val list = List(clojars, scalaz)
}

object Dependencies {

  lazy val trelloilaro = "org.tejo" %% "trelloilaro" % "0.0.3"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  lazy val scalaMock = "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"

  lazy val scaldiPlay = "org.scaldi" %% "scaldi-play" % "0.5.3"
  lazy val scaldiAkka = "org.scaldi" %% "scaldi-akka" % "0.5.3"

  lazy val clojureLib = "org.clojure" % "clojure" % "1.6.0"
  lazy val clojureTime = "clj-time" % "clj-time" % "0.9.0"

  val akkaVersion = "2.3.5"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaTest = "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion

  val akka = akkaActor :: akkaTest :: akkaSlf4j :: Nil

  //  val typesafeConsole = "com.typesafe.atmos" % "trace-play-2.2.0" % "1.3.1"

//  lazy val clojureInClojure = "org.clojure" % "clojure" % "1.5.1" % clojure.Config.name

  lazy val clara = "org.toomuchcode" % "clara-rules" % "0.8.3"

  lazy val ficus = "net.ceedubs" %% "ficus" % "1.1.1"

  lazy val logger = Seq(
    "org.clapper" %% "grizzled-slf4j" % "1.0.2",
    "ch.qos.logback" % "logback-classic" % "1.1.2")


  object WebJar {
    lazy val webjar = "org.webjars" %% "webjars-play" % "2.4.0-M2"
    lazy val bootstrap = "org.webjars" % "bootstrap" % "3.3.2"
    lazy val jQuery = "org.webjars" % "jquery" % "2.1.3"
  }

  def base = List(scalaTest, scalaMock, scaldiPlay, scaldiAkka) ++ logger
}

object IzabelaBuild extends Build {

  import BuildSettings._

  lazy val root = Project(
    id = "izabela",
    base = file("."),
    settings = buildSettings
  ).aggregate(frontProject)


  val frontName = "front"
  val frontDependencies = {
    import Dependencies._
    lazy val webJars = WebJar.bootstrap :: WebJar.jQuery :: WebJar.webjar :: Nil
    List() ++ webJars
  }
  lazy val frontProject = Project(
    id = frontName,
    base = file(frontName),
    settings = buildSettings ++ Seq(
      name := frontName,
      libraryDependencies ++= frontDependencies
    )
  ).enablePlugins(play.PlayScala).dependsOn(actorProject)


  val actorName = "actor"
  val actorDependencies = {
    import Dependencies._
    akka ++ List(ws)
  }
  lazy val actorProject = Project(
    id = actorName,
    base = file(actorName),
    settings = buildSettings ++ Seq(
      name := actorName,
      libraryDependencies ++= actorDependencies
    )
  ).dependsOn(rulesProject)

  val rulesName = "rules"
    val rulesDependencies = {
      import Dependencies._
      List(clojureLib, clojureTime, clara, trelloilaro)
  }
  lazy val rulesProject = Project(
    id = rulesName,
    base = file(rulesName),
    settings = buildSettings ++ clojure.settings ++ Seq(
      name := rulesName,
      libraryDependencies ++= rulesDependencies
    )
  )





}
