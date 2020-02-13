package org.dohrm

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{HttpApp, Route}
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContext

object Application extends App {
  val configuration: Config = ConfigFactory.load()
  implicit val actorSystem: ActorSystem = ActorSystem.apply("ac-root", configuration)
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher
  implicit val materializer: Materializer = Materializer.createMaterializer(actorSystem)

  val myOption: Option[String] = None

  val httpApp = new HttpApp {
    override protected def routes: Route = path("/") {
      get {
        complete(HttpEntity("Hello, World!"))
      }
    }
  }

  httpApp.startServer("localhost", 9000, actorSystem)

  myOption.get
}
