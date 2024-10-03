
package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import utils.DatabaseUtil
import utils.PasswordUtil
import java.sql.Connection

@Singleton
class UserController @Inject()(cc: ControllerComponents, dbUtil: DatabaseUtil) extends AbstractController(cc) {

  // Sign Up API
  def signUp = Action(parse.json) { implicit request =>
    val name = (request.body \ "name").as[String]
    val email = (request.body \ "email").as[String]
    val password = (request.body \ "password").as[String]
    val role = "user" // Assign default role

    val hashedPassword = PasswordUtil.hashPassword(password)

    val connection: Connection = dbUtil.getConnection.get

    try {
      val stmt = connection.prepareStatement(
        "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)"
      )
      stmt.setString(1, name)
      stmt.setString(2, email)
      stmt.setString(3, hashedPassword)
      stmt.setString(4, role)
      stmt.executeUpdate()

      Ok(Json.obj("status" -> "success", "message" -> "User registered successfully"))
      .withCookies(Cookie("name", name),Cookie("email", email), Cookie("role", role))
    } catch {
      case e: Exception =>
        InternalServerError(Json.obj("status" -> "error", "message" -> "User registration failed"))
    } finally {
      dbUtil.closeConnection(connection)
    }
  }

  // Sign In API
  def signIn = Action(parse.json) { implicit request =>
    val email = (request.body \ "email").as[String]
    val password = (request.body \ "password").as[String]

    val connection: Connection = dbUtil.getConnection.get

    try {
      val stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ?")
      stmt.setString(1, email)
      val rs = stmt.executeQuery()

      if (rs.next()) {
        val hashedPassword = rs.getString("password")
        val isPasswordValid = PasswordUtil.checkPassword(password, hashedPassword)

        if (isPasswordValid) {
          Ok(Json.obj("status" -> "success", "message" -> "User signed in successfully", "role" -> rs.getString("role")))
          .withCookies(Cookie("name", rs.getString("name")),Cookie("email", email), Cookie("role", rs.getString("role")))
        } else {
          Unauthorized(Json.obj("status" -> "error", "message" -> "Invalid credentials"))
        }
      } else {
        NotFound(Json.obj("status" -> "error", "message" -> "User not found"))
      }
    } catch {
      case e: Exception =>
        InternalServerError(Json.obj("status" -> "error", "message" -> "Sign in failed"))
    } finally {
      dbUtil.closeConnection(connection)
    }
  }
}