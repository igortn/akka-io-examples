package echo

import java.net.InetSocketAddress
import akka.actor.{ActorLogging, Actor, Props}
import akka.io.{Tcp, IO}

object EchoService {
  def props(endpoint: InetSocketAddress): Props = Props(classOf[EchoService], endpoint)
}

class EchoService(endpoint: InetSocketAddress) extends Actor with ActorLogging {
  import context.system

  IO(Tcp) ! Tcp.Bind(self, endpoint)

  def receive: Receive = {
    case Tcp.Connected(remote, _) =>
      log.debug("Remote address {} connected", remote)
      val handler = context.actorOf(EchoConnectionHandler.props(remote, sender))
      sender ! Tcp.Register(handler)
  }
}
