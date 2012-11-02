name := "tiramisu-app"

version := "0.1-SNAPSHOT"

organization := "com.github.igor_petruk.tiramisu"

scalaVersion := "2.10.0-RC1"

crossScalaVersions := Seq("2.10.0-RC1")

classpathTypes += "orbit"

autoScalaLibrary := true

/** Dependencies */
resolvers ++= Seq(
	"snapshots-repo" at "https://oss.sonatype.org/content/repositories/snapshots/",
	"jars-repo" at "https://oss.sonatype.org/content/groups/scala-tools/"
)

seq(webSettings :_*)

libraryDependencies <<= scalaVersion { scala_version => Seq(
	"org.scala-lang" % "scala-library" % "2.10.0-RC1",
	"org.eclipse.jetty" % "jetty-server" % "8.1.7.v20120910" % "container",
	"org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container",
	"org.eclipse.jetty" % "jetty-jsp" % "8.1.7.v20120910" % "container",
  "com.github.igor-petruk.tiramisu" %% "tiramisu-core" % "0.1-SNAPSHOT",
  "ch.qos.logback" % "logback-classic" % "1.0.7"
  )
}


/** Compilation */
javacOptions ++= Seq()

javaOptions += "-Xmx2G"

scalacOptions ++= Seq("-deprecation", "-unchecked")

//watchSources <+= baseDirectory map { _ / "src/main/webapp" }

unmanagedResourceDirectories in Compile <+= (baseDirectory) { _ / "src/main/webapp" }

maxErrors := 20

pollInterval := 1000

logBuffered := false

cancelable := true


