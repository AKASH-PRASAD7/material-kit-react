package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._

@Singleton
class DashboardController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  // Route to get dashboard data (accessible to admin and operations roles)
  def getDashboard = Action { implicit request =>
    request.headers.get("roles") match {
      case Some(role) if role == "admin" || role == "operations" =>
        val dashboardData = Json.obj(
          "categories1" -> Json.arr("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug"),
          "series1" -> Json.arr(22, 8, 35, 50, 82, 84, 77, 12),

          "categories2" -> Json.arr("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep"),
          "series2" -> Json.arr(
            Json.obj("name" -> "Team A", "data" -> Json.arr(43, 33, 22, 37, 67, 68, 37, 24, 55)),
            Json.obj("name" -> "Team B", "data" -> Json.arr(51, 70, 47, 67, 40, 37, 24, 70, 24))
          ),

          "categories3" -> Json.arr("Italy", "Japan", "China", "Canada", "France"),
          "series3" -> Json.arr(
            Json.obj("name" -> "2022", "data" -> Json.arr(44, 55, 41, 64, 22)),
            Json.obj("name" -> "2023", "data" -> Json.arr(53, 32, 33, 52, 13))
          ),

          "categories4" -> Json.arr("English", "History", "Physics", "Geography", "Chinese", "Math"),
          "series4" -> Json.arr(
            Json.obj("name" -> "Series 1", "data" -> Json.arr(80, 50, 30, 40, 100, 20)),
            Json.obj("name" -> "Series 2", "data" -> Json.arr(20, 30, 40, 80, 20, 80)),
            Json.obj("name" -> "Series 3", "data" -> Json.arr(44, 76, 78, 13, 43, 10))
          ),

          "traffic" -> Json.obj(
            "title" -> "Traffic by site",
            "list" -> Json.arr(
              Json.obj("value" -> "facebook", "label" -> "Facebook", "total" -> 323234),
              Json.obj("value" -> "google", "label" -> "Google", "total" -> 341212),
              Json.obj("value" -> "linkedin", "label" -> "Linkedin", "total" -> 411213),
              Json.obj("value" -> "twitter", "label" -> "Twitter", "total" -> 443232)
            )
          )
        )

    
        Ok(dashboardData)

      case _ =>

        Forbidden(Json.obj("status" -> "error", "message" -> "Unauthorized access"))
    }
  }
}
