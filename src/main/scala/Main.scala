import cats.effect.{ExitCode, IO, IOApp}
import io.circe.Json
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.jsonEncoder
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.Implicits.global


object Main extends IOApp {
  var tasks = List(
    Task("Learn making basic REST APIs"),
    Task("Learn GraphQL APIs"),
    Task("Learn DB drivers and ORMs"),
    Task("Learn Scala.js"),
    Task("Learn Scalajs-react"),
    Task("Learn collections"),
    Task("Learn composition")
  )

  override def run(args: List[String]): IO[ExitCode] = {
    val helloWorldService = HttpRoutes.of[IO] {
      case GET -> Root / "tasks" =>
        Ok(
          Json.obj(
            "success" := true,
            "data" := tasks
          )
        )
    }

    val app = Router("/" -> helloWorldService).orNotFound

    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(app)
      .serve
      .compile.drain
      .map(_ => ExitCode.Success)
  }
}
