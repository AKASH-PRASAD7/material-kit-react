
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
      .withCookies(Cookie("name", name,httpOnly = false, secure = true, sameSite = Some(Cookie.SameSite.None), path = "/"),Cookie("email", email,httpOnly = false, secure = true, sameSite = Some(Cookie.SameSite.None), path = "/"), Cookie("role", role,httpOnly = false, secure = true, sameSite = Some(Cookie.SameSite.None), path = "/"))
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
          .withCookies(Cookie("name", rs.getString("name"),httpOnly = false, secure = true, sameSite = Some(Cookie.SameSite.None), path = "/"),Cookie("email", email,httpOnly = false, secure = true, sameSite = Some(Cookie.SameSite.None), path = "/"), Cookie("role", rs.getString("role"),httpOnly = false, secure = true, sameSite = Some(Cookie.SameSite.None), path = "/"))
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

   // Get All Users API
  def getAllUsers = Action { implicit request =>
    // Check for admin role in headers
    request.headers.get("roles") match {
      case Some(role) if role == "admin" =>
        val connection: Connection = dbUtil.getConnection.get
        try {
          val stmt = connection.prepareStatement("SELECT * FROM users")
          val rs = stmt.executeQuery()

          val users = scala.collection.mutable.ListBuffer[JsObject]()
          while (rs.next()) {
            val user = Json.obj(
              "id" -> rs.getString("id"),
              "name" -> rs.getString("name"),
              "email" -> rs.getString("email"),
              "role" -> rs.getString("role")
            )
            users += user
          }

          Ok(Json.toJson(users.toList))
        } catch {
          case e: Exception =>
            InternalServerError(Json.obj("status" -> "error", "message" -> "Failed to retrieve users"))
        } finally {
          dbUtil.closeConnection(connection)
        }
      case _ =>
        Forbidden(Json.obj("status" -> "error", "message" -> "Unauthorized access"))
    }
  }

   // Update User Role API
  def updateUserRole = Action(parse.json) { implicit request =>
    // Check for admin role in headers
    request.headers.get("roles") match {
      case Some(role) if role == "admin" =>
        val userId = (request.body \ "id").validate[Int]
        val newRole = (request.body \ "role").as[String]

        // Validate newRole
        val validRoles = Set("admin", "user", "sales", "operations")
        if (!validRoles.contains(newRole)) {
          BadRequest(Json.obj("status" -> "error", "message" -> s"Invalid role: $newRole"))
        } else {
          val connection: Connection = dbUtil.getConnection.get

          try {
            val stmt = connection.prepareStatement("UPDATE users SET role = ? WHERE id = ?")
            stmt.setString(1, newRole)
            stmt.setInt(2, userId.get)
            val rowsUpdated = stmt.executeUpdate()

            if (rowsUpdated > 0) {
              Ok(Json.obj("status" -> "success", "message" -> "User role updated successfully"))
            } else {
              NotFound(Json.obj("status" -> "error", "message" -> "User not found"))
            }
          } catch {
            case e: Exception =>
              InternalServerError(Json.obj("status" -> "error", "message" -> s"Failed to update user role:${e.getClass.getSimpleName} - ${e.getMessage}"))
          } finally {
            dbUtil.closeConnection(connection)
          }
        }
      case _ =>
        Forbidden(Json.obj("status" -> "error", "message" -> "Unauthorized access"))
    }
  }
}