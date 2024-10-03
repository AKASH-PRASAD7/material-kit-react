case class User(id: Option[Int], name: String, email: String, password: String, role: String)

object User {
  import play.api.libs.json._

  implicit val userFormat: OFormat[User] = Json.format[User]
}
