package utils

import java.sql.{Connection, DriverManager}
import javax.inject.Singleton

@Singleton
class DatabaseUtil {
  // Update the connection string to connect to the Neon DB
  private val url = "jdbc:postgresql://ep-small-night-a1fk3m8k-pooler.ap-southeast-1.aws.neon.tech/testDB?sslmode=require"
  private val user = "Employee_owner"
  private val password = "BlCgmhV9jbe1"

  def getConnection: Option[Connection] = {
    try {
      Class.forName("org.postgresql.Driver")
      Some(DriverManager.getConnection(url, user, password))
    } catch {
      case e: Exception =>
        e.printStackTrace()
        None
    }
  }

  def closeConnection(connection: Connection): Unit = {
    try {
      connection.close()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
