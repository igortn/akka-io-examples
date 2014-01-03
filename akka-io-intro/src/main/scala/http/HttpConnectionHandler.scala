package http

import java.net.InetSocketAddress
import akka.actor._
import spray.http._
import akka.io.Tcp
import spray.http.HttpRequest
import akka.actor.Terminated
import spray.http.HttpResponse

object HttpConnectionHandler {
  def props(remote: InetSocketAddress, connection: ActorRef) = Props(classOf[HttpConnectionHandler], remote, connection)
}

class HttpConnectionHandler(remote: InetSocketAddress, connection: ActorRef) extends Actor with ActorLogging {
  context.watch(connection)

  def receive: Receive = {
    case HttpRequest(HttpMethods.GET, uri, _, _, _) =>
      log.debug("Serving request from {}", remote)
      sender ! HttpResponse(entity = HttpEntity("Response for URI: " + uri.path + ". Client: " + remote + "."))

    case _: Tcp.ConnectionClosed =>
      log.debug("Connection {} closed", remote)
      context.stop(self)

    case Terminated(_) =>
      log.debug("Connection {} died", remote)
      context.stop(self)
  }
}
