package echo

import akka.actor._
import java.net.InetSocketAddress
import akka.io.Tcp

object EchoConnectionHandler {
  def props(remote: InetSocketAddress, connection: ActorRef): Props = Props(classOf[EchoConnectionHandler], remote, connection)
}

class EchoConnectionHandler(remote: InetSocketAddress, connection: ActorRef) extends Actor with ActorLogging {

  context.watch(connection)

  def receive: Receive = {
    case Tcp.Received(data) =>
      val text = data.utf8String.trim
      log.debug("Received '{}' from remote address {}", text, remote)
      text match {
        case "close" => context.stop(self)
        case _ => sender ! Tcp.Write(data)
      }

    case _: Tcp.ConnectionClosed =>
      log.debug("Remote address {} closed connection", remote)
      context.stop(self)

    case Terminated(_) =>
      log.debug("Remote connection {} died", remote)
      context.stop(self)
  }

}
