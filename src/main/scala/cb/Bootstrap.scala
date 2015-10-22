package cb

import akka.actor.{Inbox, Props, ActorSystem}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Bootstrap extends App {

  val system = ActorSystem("cb")

  sys.addShutdownHook({
    system.terminate()

    Await.result(system.whenTerminated, Duration.Inf)
  })

  val accounting = system.actorOf(Props[Accounting], "accounting")

  val inbox = Inbox.create(system)

  inbox.send(accounting, "init")

}
