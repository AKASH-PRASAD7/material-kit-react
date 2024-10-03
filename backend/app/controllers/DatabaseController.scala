package controllers

import javax.inject._
import play.api.mvc._
import utils.DatabaseUtil

@Singleton
class DatabaseController @Inject()(cc: ControllerComponents, dbUtil: DatabaseUtil) extends AbstractController(cc) {

  def testConnection = Action { implicit request =>
    val connOpt = dbUtil.getConnection

    connOpt match {
      case Some(conn) =>
        dbUtil.closeConnection(conn)
        Ok("Database connection successful!")
      case None =>
        InternalServerError("Failed to connect to the database.")
    }
  }
}