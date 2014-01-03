name := "Akka IO Intro"

scalaVersion := "2.10.3"

scalacOptions ++= List("-feature","-deprecation")

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= Deps.depList

ideaExcludeFolders ++= List(".idea",".idea_modules")

