package models

import scala.collection.mutable

case class User(name: String, email: String, hashedPassword: String, role: String)

object User {
  private val users = mutable.Map[String, User]()

  def create(name: String, email: String, hashedPassword: String, role: String): Unit = {
    users(email) = User(name, email, hashedPassword, role)
  }

  def findByEmail(email: String): Option[User] = {
    users.get(email)
  }
}
