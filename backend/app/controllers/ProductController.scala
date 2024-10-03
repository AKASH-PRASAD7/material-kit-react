package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import utils.DatabaseUtil
import models.Product
import java.sql.Connection

@Singleton
class ProductController @Inject()(cc: ControllerComponents, dbUtil: DatabaseUtil) extends AbstractController(cc) {

  // Create Product API
  def createProduct = Action(parse.json) { implicit request =>
    val name = (request.body \ "name").as[String]
    val price = (request.body \ "price").as[BigDecimal]
    val status = (request.body \ "status").as[String]
    val coverUrl = (request.body \ "coverUrl").as[String]
    val colors = (request.body \ "colors").as[String]
    val priceSale = (request.body \ "priceSale").asOpt[BigDecimal]

    val connection: Connection = dbUtil.getConnection.get

    try {
      val stmt = connection.prepareStatement(
        "INSERT INTO products (name, price, status, coverurl, colors, pricesale) VALUES (?, ?, ?, ?, ?, ?)"
      )
      stmt.setString(1, name)
      stmt.setBigDecimal(2, price.underlying()) // Convert to java.math.BigDecimal
      stmt.setString(3, status)
      stmt.setString(4, coverUrl)
      stmt.setString(5, colors)
      priceSale match {
        case Some(value) => stmt.setBigDecimal(6, value.underlying())
        case None => stmt.setNull(6, java.sql.Types.DECIMAL)
      }
      stmt.executeUpdate()

      Ok(Json.obj("status" -> "success", "message" -> "Product created successfully"))
    } catch {
      case e: Exception =>
        InternalServerError(Json.obj("status" -> "error",  "message" -> s"Product creation failed: ${e.getClass.getSimpleName} - ${e.getMessage}"))
    } finally {
      dbUtil.closeConnection(connection)
    }
  }

  // Get All Products API
 def getAllProducts = Action { implicit request =>
  val connection: Connection = dbUtil.getConnection.get

  try {
    val stmt = connection.prepareStatement("SELECT * FROM products")
    val rs = stmt.executeQuery()

    val products = scala.collection.mutable.ListBuffer[Product]()
    while (rs.next()) {
      val product = Product(
        id = rs.getString("id"),
        name = rs.getString("name"),
        price = rs.getString("price"), // Assume price is a string
        status = rs.getString("status"),
        coverUrl = rs.getString("coverurl"),
        colors = rs.getString("colors"),
        priceSale = rs.getString("pricesale"), // Assume priceSale is a string
        createdAt = rs.getString("created_at") // Ensure createdAt is populated
      )
      products += product
    }

    Ok(Json.toJson(products.toList))
  } catch {
    case e: Exception =>
      InternalServerError(Json.obj("status" -> "error", "message" -> s"Failed to retrieve products: ${e.getClass.getSimpleName} - ${e.getMessage}"))
  } finally {
    dbUtil.closeConnection(connection)
  }
}
}
