package admin.produit

import baseDonne.DBConnection

import java.sql.Statement

object ProduitDAO {
  def addProduit(produit: Produit): Int = {
    // Create a connection to the database
    val conn = DBConnection.getConnection
    //Create statement
    val statement = conn.prepareStatement("INSERT INTO produit (id_produit, nom_produit, categorie_produit, prix) VALUES (?,?,?,?)",
      Statement.RETURN_GENERATED_KEYS)

    // Set the values for the prepared statement
    statement.setInt(1, produit.id_produit)
    statement.setString(2, produit.nom_produit)
    statement.setString(3, produit.categorie_produit)
    statement.setDouble(4, produit.prix)

    // Execute the statement
    statement.executeUpdate()

    // Get the generated id
    val rs = statement.getGeneratedKeys
    var id = -1
    if (rs.next()) {
      id = rs.getInt(1)
    }

    // Close the connection
    statement.close()
    conn.close()

    id
  }

  def updateProduit(produit: Produit): Int = {
    // Create a connection to the database
    val conn = DBConnection.getConnection
    // Create the statement
    val statement = conn.prepareStatement("UPDATE produit SET nom_produit=?, categorie_produit=?, prix=? WHERE id_produit=?")

    // Set the values for the prepared statement
    statement.setString(1, produit.nom_produit)
    statement.setString(2, produit.categorie_produit)
    statement.setDouble(3, produit.prix)
    statement.setInt(4, produit.id_produit)

    // Execute the statement
    val rowsAffected = statement.executeUpdate()

    // Close the connection
    statement.close()
    conn.close()

    rowsAffected
  }

  def deleteProduit(produitId: Int): Int = {
    // Create a connection to the database
    val conn = DBConnection.getConnection

    // Create the statement
    val statement = conn.prepareStatement("DELETE FROM produit WHERE id_produit=?")

    // Set the id for the prepared statement
    statement.setInt(1, produitId)

    // Execute the statement
    val rowsAffected = statement.executeUpdate()

    // Close the connection
    statement.close()
    conn.close()

    rowsAffected
  }
}