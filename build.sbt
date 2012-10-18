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

libraryDependencies <<= scalaVersion { scala_version => Seq(
	"org.scala-lang" % "scala-library" % "2.10.0-RC1",
	"org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" artifacts (Artifact("javax.servlet", "jar", "jar")),
	"org.eclipse.jetty" % "jetty-webapp" % "8.1.4.v20120524" artifacts (Artifact("jetty-webapp", "jar", "jar")),
  "com.github.igor-petruk.tiramisu" %% "tiramisu-core" % "0.1-SNAPSHOT"
  )
}


/** Compilation */
javacOptions ++= Seq()

javaOptions += "-Xmx2G"

scalacOptions ++= Seq("-deprecation", "-unchecked")

maxErrors := 20 

pollInterval := 1000

logBuffered := false

cancelable := true


