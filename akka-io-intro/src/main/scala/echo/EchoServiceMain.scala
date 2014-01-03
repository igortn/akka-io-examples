package echo

import akka.actor.{Props, ActorLogging, Actor, ActorSystem}
import java.net.InetSocketAddress
import akka.io.{Tcp, IO}


object EchoServiceMain extends App {

  val system = ActorSystem("EchoServiceSystem")
  val endpoint = new InetSocketAddress("localhost", 11111)
  
  system.actorOf(EchoService.props(endpoint), "EchoService")

  readLine("Hit ENTER to exit")
  system.shutdown()
}



