package admin.orders

import baseDonne.DBConnection

import java.sql.Statement

object ProduitDAO {




  def update(commande: CommandeDetails): Unit = {
    val conn =DBConnection.getConnection
    try {
      val stmt = conn.prepareStatement("UPDATE commande_details SET id_produit=?, quantite=?, total=? WHERE id_commande_details=?")
      stmt.setInt(1, commande.id_produit)
      stmt.setInt(2, commande.quantite)
      stmt.setDouble(3, commande.total)

      stmt.setInt(4, commande.id_commande_details)
      stmt.executeUpdate()
    } finally {
      conn.close()
    }
  }

  def delete(id_commande_details: Int): Unit = {
    val conn = DBConnection.getConnection
    try {
      val stmt = conn.prepareStatement("DELETE FROM commande_details WHERE id_commande_details=?")
      stmt.setInt(1, id_commande_details)
      stmt.executeUpdate()
    } finally {
      conn.close()
    }
  }
}
