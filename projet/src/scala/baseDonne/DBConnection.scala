package baseDonne
import saission.CaissierSaission

import java.sql.{Connection, DriverManager, ResultSet}


object DBConnection {
  private val url = "jdbc:mysql://localhost:3306/scala?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
  private val username = "root"
  private val password = ""

  def getConnection: Connection = {
    // Créez une connexion à la base de données à l'aide des informations de connexion
    DriverManager.getConnection(url, username, password)
  }
  def isValidCredentialAdmin(username: String, password: String): Boolean = {
    val connection = getConnection
    var boolean=false
    try {
      val statement = connection.createStatement
      val resultSet = statement.executeQuery(s"SELECT * FROM administrateur WHERE username = '$username' AND password = '$password'")
    resultSet.next() // Si l'enregistrement existe, le curseur se déplace à l'enregistrement suivant

    } finally {
    //  connection.close()
    }
  }
  def isValidCredentialUser(username: String, password: String): Boolean = {
    val connection = getConnection
    var boolean=false
    try {
      val statement = connection.createStatement
      val resultSet = statement.executeQuery(s"SELECT * FROM caissier WHERE username = '$username' AND password = '$password'")
      resultSet.next() // Si l'enregistrement existe, le curseur se déplace à l'enregistrement suivant

    } finally {
      //  connection.close()
    }
  }


}