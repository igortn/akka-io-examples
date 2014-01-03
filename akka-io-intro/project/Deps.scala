import sbt._

object Libs {
  val akkaVersion = "2.2.3"
  val sprayVersion = "1.2.0"
  val logbackVersion = "1.0.13"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val logback = "ch.qos.logback" % "logback-classic" % logbackVersion
  val sprayCan = "io.spray" % "spray-can" % sprayVersion
}

object Deps {
  import Libs._

  val depList = List(akkaActor, akkaSlf4j, logback, sprayCan)
}

