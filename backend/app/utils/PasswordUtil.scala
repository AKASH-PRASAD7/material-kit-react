package utils

import org.mindrot.jbcrypt.BCrypt

object PasswordUtil {
  // Hash the password
  def hashPassword(password: String): String = {
    BCrypt.hashpw(password, BCrypt.gensalt())
  }

  // Check if the password matches the hashed password
  def checkPassword(candidate: String, hashed: String): Boolean = {
    BCrypt.checkpw(candidate, hashed)
  }
}
