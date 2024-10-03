// package utils

// import com.auth0.jwt.JWT
// import com.auth0.jwt.algorithms.Algorithm
// import java.util.Date

// object JwtUtil {
//   private val secretKey = "yourSecretKey"
//   private val algorithm = Algorithm.HMAC256(secretKey)
//   private val expirationTime = 86400000 // 1 day in milliseconds

//   // Generate a JWT token
//   def createToken(name:String,email: String, role: String): String = {
//     JWT.create()
//         .withClaim("name", name)
//       .withClaim("email", email)
//       .withClaim("role", role)
//       .withIssuedAt(new Date())
//       .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
//       .sign(algorithm)
//   }

//   // Decode and verify JWT token
//   def verifyToken(token: String): Option[Map[String, Any]] = {
//     try {
//       val verifier = JWT.require(algorithm).build()
//       val decodedJWT = verifier.verify(token)
//         val name = decodedJWT.getClaim("name").asString()
//       val email = decodedJWT.getClaim("email").asString()
//       val role = decodedJWT.getClaim("role").asString()
//       Some(Map("name"->name,"email" -> email, "role" -> role))
//     } catch {
//       case _: Exception => None
//     }
//   }
// }
