package controllers

import javax.inject._
import play.api.mvc._

@Inject
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getUsers = Action {
    Ok("List of users")
  }

  def getUser(id: Long) = Action {
    Ok(s"Details of user with id $id")
  }

  def createUser = Action { request =>
    val userData = request.body.asJson.get
    Ok(s"User created with data: $userData")
  }

  def updateUser(id: Long) = Action { request =>
    val updatedData = request.body.asJson.get
    Ok(s"User with id $id updated with data: $updatedData")
  }

  def deleteUser(id: Long) = Action {
    Ok(s"User with id $id deleted")
  }
}
