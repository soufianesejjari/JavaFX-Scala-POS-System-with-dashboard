package baseDonne

import admin.caissier.Caissier

import java.sql.{Connection, PreparedStatement, ResultSet}

object CaissierDAO {
  def getConnection(): Connection = {
    DBConnection.getConnection
  }

  def addCaissier(caissier: Caissier): Unit = {
    val connection = getConnection()
    try {
      val statement = connection.prepareStatement(
        "INSERT INTO caissier (id_admin, nom_caissier, prenom_caissier, username, password, ville, telephone) VALUES (?, ?, ?, ?, ?, ?, ?)"
      )
      statement.setString(1, caissier.id_admin)
      statement.setString(2, caissier.nom_caissier)
      statement.setString(3, caissier.prenom_caissier)
      statement.setString(4, caissier.username)
      statement.setString(5, caissier.password)
      statement.setString(6, caissier.ville)
      statement.setInt(7, caissier.telephone)
      statement.executeUpdate()
    } finally {
      connection.close()
    }
  }

  def updateCaissier(caissier: Caissier): Unit = {
    val connection = getConnection()
    try {
      val statement = connection.prepareStatement(
        "UPDATE caissier SET id_admin = ?, nom_caissier = ?, prenom_caissier = ?, username = ?, password = ?, ville = ?, telephone = ? WHERE id_caissier = ?"
      )
      statement.setString(1, caissier.id_admin)
      statement.setString(2, caissier.nom_caissier)
      statement.setString(3, caissier.prenom_caissier)
      statement.setString(4, caissier.username)
      statement.setString(5, caissier.password)
      statement.setString(6, caissier.ville)
      statement.setInt(7, caissier.telephone)
      statement.setString(8, caissier.id_caissier)
      statement.executeUpdate()
    } finally {
      connection.close()
    }
  }

  def deleteCaissier(id_caissier: Int): Unit = {
    val connection = getConnection()
    try {
      val statement = connection.prepareStatement("DELETE FROM caissier WHERE id_caissier = ?")
      statement.setInt(1, id_caissier)
      statement.executeUpdate()
    } finally {
      connection.close()
    }
  }
}