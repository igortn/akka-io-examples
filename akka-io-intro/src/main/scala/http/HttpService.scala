package http

import akka.actor.{Props, ActorLogging, Actor}
import akka.io.IO
import spray.can.Http


object HttpService {
  def props(host:String, port: Int): Props = Props(classOf[HttpService], host, port)
}

class HttpService(host: String, port: Int) extends Actor with ActorLogging {
  import context.system

  IO(Http) ! Http.Bind(self, host, port)

  def receive: Receive = {
    case Http.Connected(remote, _) =>
      log.debug("Remote address {} connected", remote)
      val handler = context.actorOf(HttpConnectionHandler.props(remote, sender))
      sender ! Http.Register(handler)
  }

}
