import io.circe.Encoder
import io.circe.generic.semiauto._


case class Task(title: String, completed: Boolean = false)

object Task {
  implicit val encoder: Encoder[Task] = deriveEncoder[Task]
}
