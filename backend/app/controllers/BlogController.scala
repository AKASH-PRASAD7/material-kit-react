package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import utils.DatabaseUtil
import models.Blog
import java.sql.Connection

@Singleton
class BlogController @Inject()(cc: ControllerComponents, dbUtil: DatabaseUtil) extends AbstractController(cc) {

  // Create Blog API
  def createBlog = Action(parse.json) { implicit request =>
    val title = (request.body \ "title").as[String]
    val coverUrl = (request.body \ "coverUrl").as[String]
    val totalViews = (request.body \ "totalViews").as[Int]
    val description = (request.body \ "description").as[String]
    val totalShares = (request.body \ "totalShares").as[Int]
    val totalComments = (request.body \ "totalComments").as[Int]
    val totalFavorites = (request.body \ "totalFavorites").as[Int]
    val postedAt = (request.body \ "postedAt").as[String]
    val name = (request.body \ "name").as[String]
    val avatarUrl = (request.body \ "avatarUrl").as[String]

    val connection: Connection = dbUtil.getConnection.get

    try {
      val stmt = connection.prepareStatement(
        "INSERT INTO blogs (title, coverurl, totalviews, description, totalshares, totalcomments, totalfavorites, postedat, name, avatarurl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
      )
      stmt.setString(1, title)
      stmt.setString(2, coverUrl)
      stmt.setInt(3, totalViews)
      stmt.setString(4, description)
      stmt.setInt(5, totalShares)
      stmt.setInt(6, totalComments)
      stmt.setInt(7, totalFavorites)
      stmt.setString(8, postedAt)
      stmt.setString(9, name)
      stmt.setString(10, avatarUrl)
      stmt.executeUpdate()

      Ok(Json.obj("status" -> "success", "message" -> "Blog created successfully"))
    } catch {
      case e: Exception =>
        InternalServerError(Json.obj("status" -> "error", "message" -> s"Blog creation failed: ${e.getClass.getSimpleName} - ${e.getMessage}"))
    } finally {
      dbUtil.closeConnection(connection)
    }
  }

  // Get All Blogs API
  def getAllBlogs = Action { implicit request =>
  // Extract the role from the headers
  request.headers.get("roles") match {
    case Some(role) if role == "admin" || role == "sales" || role == "operations" =>
      // If the role is admin, sales, or operations, proceed with fetching blogs
      val connection: Connection = dbUtil.getConnection.get

      try {
        val stmt = connection.prepareStatement("SELECT * FROM blogs")
        val rs = stmt.executeQuery()

        val blogs = scala.collection.mutable.ListBuffer[Blog]()
        while (rs.next()) {
          val blog = Blog(
            id = rs.getString("id"),
            title = rs.getString("title"),
            coverUrl = rs.getString("coverurl"),
            totalViews = rs.getInt("totalviews"),
            description = rs.getString("description"),
            totalShares = rs.getInt("totalshares"),
            totalComments = rs.getInt("totalcomments"),
            totalFavorites = rs.getInt("totalfavorites"),
            postedAt = rs.getString("postedat"),
            name = rs.getString("name"),
            avatarUrl = rs.getString("avatarurl")
          )
          blogs += blog
        }

        Ok(Json.toJson(blogs.toList))
      } catch {
        case e: Exception =>
          InternalServerError(Json.obj("status" -> "error", "message" -> s"Failed to retrieve blogs: ${e.getClass.getSimpleName} - ${e.getMessage}"))
      } finally {
        dbUtil.closeConnection(connection)
      }

    case _ =>
      // If the role is not allowed, return 403 Forbidden
      Forbidden(Json.obj("status" -> "error", "message" -> "Unauthorized access"))
  }
}

}
