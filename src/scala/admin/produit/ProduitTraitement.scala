package admin.produit

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
import java.util
import java.util.ResourceBundle

class ProduitTraitement extends Initializable  {

  @FXML
  var addCaissierButton  :Button = _

  @FXML
  private var  tableCaissier: TableView[Produit]=_
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    implimentTable()
//    val caissierTable = new TableView[Caissier]()

  //  tableCaissier.setEditable(true)

  //  scrollPaneCaisser.getChildren.add(caissierTable)
    tableCaissier.setRowFactory(new Callback[TableView[Produit], TableRow[Produit]]() {
      override def call(tableView: TableView[Produit]): TableRow[Produit] = {
        val row = new TableRow[Produit]()
        row.setOnMouseClicked(new EventHandler[MouseEvent]() {
          override def handle(event: MouseEvent): Unit = {
            if (event.getButton == MouseButton.PRIMARY && event.getClickCount == 2) {
              val selectedFruit: Produit = row.getItem
              // Affichez les détails du fruit sélectionné ici
              print("sssssssssssssssssssssssss", selectedFruit)
              val editor = new ProduitEditorDialog(selectedFruit,0)
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

    addCaissierButton.setOnAction(_ => {
      val caissierEditorDialog = new produit.ProduitEditorDialog( new Produit(0,"","",0),1)
      caissierEditorDialog.showAndWait()

      implimentTable()
    })
  }
  def implimentTable():Unit={
    val produitTable = new TableView[Produit]()
    val idCol = new TableColumn[Produit, String]("id_produit")
    idCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.id_produit.toString))
    val nomCol = new TableColumn[Produit, String]("nom_produit")
    nomCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.nom_produit.toString))
    val categorieCol = new TableColumn[Produit, String]("categorie_produit")
    categorieCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.categorie_produit.toString))
    val prixCol = new TableColumn[Produit, String]("prix")
    prixCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.prix.toString))

    tableCaissier.getColumns().setAll(idCol, nomCol, categorieCol, prixCol)
//    produitTable.setItems(loadData())
    val datas=loadData()
    //  tableCaissier.getItems(datas)
    tableCaissier.getItems.setAll(datas)
  }
//  tableCaissier.setEditable(true)




 // case class Caissier(val id_caissier: String, val id_admin: String, val nom_caissier: String, val prenom_caissier: String, val username: String, val password: String, val ville: String, val telephone: Int)





  def loadData(): ObservableList[Produit] = {
    val data: ObservableList[Produit] = javafx.collections.FXCollections.observableArrayList[Produit]()

    try {
      // Connect to the database
      val conn = DBConnection.getConnection


      // Execute the query
      val statement = conn.createStatement()
      val resultSet = statement.executeQuery("SELECT id_produit, nom_produit, categorie_produit, prix FROM produit")

      // Extract the data from the result set
      while (resultSet.next()) {
        val id = resultSet.getInt("id_produit")
        val nom = resultSet.getString("nom_produit")
        val categorie = resultSet.getString("categorie_produit")
        val prix = resultSet.getDouble("prix")
        val produit = new Produit(id, nom, categorie, prix)
        data.add(produit)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    data
  }
}
