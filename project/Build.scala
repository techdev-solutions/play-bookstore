import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play-bookstore"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    javaCore,
    javaJdbc,
    javaEbean,
    "org.mindrot" % "jbcrypt" % "0.3m",
    "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
    "be.objectify" %% "deadbolt-java" % "2.1",
    "com.amazonaws" % "aws-java-sdk" % "1.6.0.1",
    "org.imgscalr" % "imgscalr-lib" % "4.2",
    "org.apache.commons" % "commons-lang3" % "3.2.1"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases"))(Resolver.ivyStylePatterns)
  )

}
