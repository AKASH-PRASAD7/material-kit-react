package models

import play.api.libs.json._

case class Blog(
  id: String,
  title: String,
  coverUrl: String,
  totalViews: Int,
  description: String,
  totalShares: Int,
  totalComments: Int,
  totalFavorites: Int,
  postedAt: String,
  name: String,
  avatarUrl: String
)

object Blog {
  implicit val blogWrites: Writes[Blog] = new Writes[Blog] {
    def writes(blog: Blog): JsValue = Json.obj(
      "id" -> blog.id,
      "title" -> blog.title,
      "coverUrl" -> blog.coverUrl,
      "totalViews" -> blog.totalViews,
      "description" -> blog.description,
      "totalShares" -> blog.totalShares,
      "totalComments" -> blog.totalComments,
      "totalFavorites" -> blog.totalFavorites,
      "postedAt" -> blog.postedAt,
      "name" -> blog.name,
      "avatarUrl" -> blog.avatarUrl
    )
  }

  // JSON deserializer for Blog (if needed)
  implicit val blogReads: Reads[Blog] = Json.reads[Blog]
}
