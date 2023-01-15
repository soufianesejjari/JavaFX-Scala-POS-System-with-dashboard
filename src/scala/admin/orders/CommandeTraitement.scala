package admin.orders

import admin.caissier.{Caissier, CaissierEditorDialog}
import admin.produit
import baseDonne.{CaissierDAO, DBConnection}
import javafx.beans.property.SimpleStringProperty
import javafx.collections.{FXCollections, ObservableList}
import javafx.event.EventHandler
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.{MouseButton, MouseEvent}
import javafx.scene.layout.Pane
import javafx.util.Callback

import java.net.URL
import java.time.LocalDate
import java.util
import java.util.{Properties, ResourceBundle}

class CommandeTraitement extends Initializable  {

  @FXML
  var addCaissierButton  :Button = _

  @FXML
  private var  tableCaissier: TableView[CommandeDetails]=_
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    implimentTable()
//    val caissierTable = new TableView[Caissier]()

  //  tableCaissier.setEditable(true)

  //  scrollPaneCaisser.getChildren.add(caissierTable)
    tableCaissier.setRowFactory(new Callback[TableView[CommandeDetails], TableRow[CommandeDetails]]() {
      override def call(tableView: TableView[CommandeDetails]): TableRow[CommandeDetails] = {
        val row = new TableRow[CommandeDetails]()
        row.setOnMouseClicked(new EventHandler[MouseEvent]() {
          override def handle(event: MouseEvent): Unit = {
            if (event.getButton == MouseButton.PRIMARY && event.getClickCount == 2) {
              val selectedFruit: CommandeDetails = row.getItem
              // Affichez les détails du fruit sélectionné ici
              print("sssssssssssssssssssssssss", selectedFruit)
              val editor = new CommandeEditorDialog(selectedFruit)
              editor.showAndWait()
    implimentTable()
              //  getInfoProduct(selectedFruit)
            }
          }
        })
        row
      }

    })
  //  tableCaissier.refresh()

 //  addCaissierButton.setOnAction(_ => {
 //    val caissierEditorDialog = new CommandeEditorDialog( new CommandeDetails(0,"","",0),1)
 //    caissierEditorDialog.showAndWait()

 //    implimentTable()
 //  })
  }
  def implimentTable():Unit={
  //  val commandeTable = new TableView[CommandeDetails]()
    val idCol = new TableColumn[CommandeDetails, String]("id_commande_details")
    idCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.id_commande_details.toString))
    val idProduitCol = new TableColumn[CommandeDetails, String]("id_produit")
    idProduitCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.id_produit.toString))
    val quantiteCol = new TableColumn[CommandeDetails, String]("quantite")
    quantiteCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.quantite.toString))
    val dateCommandeCol = new TableColumn[CommandeDetails, String]("date_commande")
    dateCommandeCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.date_commande.toString))
    val totalCol = new TableColumn[CommandeDetails, String]("total")
    totalCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.total.toString))
    val caissierNameCol = new TableColumn[CommandeDetails, String]("caissier_name")
    caissierNameCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.caissier_name.toString))

    tableCaissier.getColumns().setAll(idCol, idProduitCol, quantiteCol, dateCommandeCol, totalCol, caissierNameCol)
    tableCaissier.setItems(loadData())
  }
//  tableCaissier.setEditable(true)




 // case class Caissier(val id_caissier: String, val id_admin: String, val nom_caissier: String, val prenom_caissier: String, val username: String, val password: String, val ville: String, val telephone: Int)





  def loadData(): ObservableList[CommandeDetails] = {
    val data = FXCollections.observableArrayList[CommandeDetails]()


    val conn = DBConnection.getConnection

    try {
      val stmt = conn.createStatement()
      val rs = stmt.executeQuery("SELECT id_commande_details, id_produit, quantite, date_commande, total, nom_caissier FROM commande_details co JOIN commandes c  ON co.`id_commande`= c.id_commande JOIN caissier p ON c.id_caissier = p.id_caissier")
      while (rs.next()) {
        val id_commande_details = rs.getInt("id_commande_details")
        val id_produit = rs.getInt("id_produit")
        val quantite = rs.getInt("quantite")
        val date_commande = rs.getDate("date_commande").toLocalDate
        val total = rs.getDouble("total")
        val caissier_name = rs.getString("nom_caissier")
        data.add(new CommandeDetails(id_commande_details, id_produit, quantite, date_commande, total, caissier_name))
      }
    } finally {
      conn.close()
    }

    data
  }}
