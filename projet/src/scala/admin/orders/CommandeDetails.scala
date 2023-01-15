package admin.orders

import java.time.LocalDate

case class CommandeDetails(var id_commande_details: Int, var id_produit: Int, var quantite: Int, var date_commande: LocalDate, var total: Double, var caissier_name: String){


}