package http

import akka.actor.ActorSystem


object HttpServiceMain extends App {

  val system = ActorSystem("HttpServiceSystem")
  system.actorOf(HttpService.props("localhost", 8888))

  readLine("Hit ENTER to exit")
  system.shutdown()
}
