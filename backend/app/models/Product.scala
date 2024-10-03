package models

import play.api.libs.json._
import java.math.BigDecimal
import java.time.LocalDateTime

case class Product(
  id: String,
  name: String,
  price: String, // Assume this is java.math.BigDecimal from database
  status: String,
  coverUrl: String,
  colors: String,
  priceSale: String, // Option for nullable priceSale
  createdAt: String
)

object Product {
  // JSON serializer for Product
  implicit val productWrites: Writes[Product] = new Writes[Product] {
    def writes(product: Product): JsValue = Json.obj(
      "id" -> product.id,
      "name" -> product.name,
      "price" -> product.price,
      "status" -> product.status,
      "coverUrl" -> product.coverUrl,
      "colors" -> product.colors,
      "priceSale" -> product.priceSale,
      "createdAt" -> product.createdAt
    )
  }

  // JSON deserializer for Product (if needed)
  implicit val productReads: Reads[Product] = Json.reads[Product]
}
