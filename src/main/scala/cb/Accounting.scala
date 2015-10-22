package cb

import akka.actor.Actor.Receive
import akka.actor.{ActorLogging, Actor}

/** Sealed keyword : http://www.scala-lang.org/old/node/123 */
sealed trait AccountingCmd
case class Get(accountNumber: String) extends AccountingCmd

class Accounting extends Actor with ActorLogging {

  log.debug("Accounting actor starting")

  override def receive: Receive = {
    case Get(accountNumber) =>
      context.actorSelection(accountNumber).resolveOne().onComplete( account =>
        sender() ! account
      )
    case msg: Any => {
      log.warning("Accounting.receive missing implementation {}, {}", sender(), msg)
    }
  }

}
