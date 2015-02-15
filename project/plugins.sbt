resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.2")

// from local repository
addSbtPlugin("com.unhandledexpression" % "sbt-clojure" % "0.1.1")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.0-M2")