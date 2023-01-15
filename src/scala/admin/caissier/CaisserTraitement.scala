package admin.caissier
import admin.caissier.Caissier
import admin.caissier.{Caissier, CaissierEditorDialog}
import baseDonne.{CaissierDAO, DBConnection}
import javafx.beans.property.SimpleStringProperty
import javafx.collections.{FXCollections, ObservableList}
import javafx.event.EventHandler
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.*
import javafx.scene.input.{MouseButton, MouseEvent}
import javafx.scene.layout.Pane
import javafx.util.Callback

import java.net.URL
import java.util
import java.util.ResourceBundle

class CaisserTraitement extends Initializable  {

  @FXML
  var addCaissierButton  :Button = _

  @FXML
  private var  tableCaissier: TableView[Caissier]=_
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    implimentTable()
//    val caissierTable = new TableView[Caissier]()

  //  tableCaissier.setEditable(true)

  //  scrollPaneCaisser.getChildren.add(caissierTable)
    tableCaissier.setRowFactory(new Callback[TableView[Caissier], TableRow[Caissier]]() {
      override def call(tableView: TableView[Caissier]): TableRow[Caissier] = {
        val row = new TableRow[Caissier]()
        row.setOnMouseClicked(new EventHandler[MouseEvent]() {
          override def handle(event: MouseEvent): Unit = {
            if (event.getButton == MouseButton.PRIMARY && event.getClickCount == 2) {
              val selectedFruit: Caissier = row.getItem
              // Affichez les détails du fruit sélectionné ici
              print("sssssssssssssssssssssssss", selectedFruit)
              val editor = new CaissierEditorDialog(selectedFruit,0)
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
      val caissierEditorDialog = new CaissierEditorDialog( new Caissier("","","","","","","",0),1)
      caissierEditorDialog.showAndWait()

      implimentTable()
    })
  }
  def implimentTable():Unit={
    val idCol = new TableColumn[Caissier, String]("id_caissier")
    idCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.id_caissier.toString))

    val idAdminCol = new TableColumn[Caissier, String]("id_admin")
    idAdminCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.id_admin.toString))
    val nomCol = new TableColumn[Caissier, String]("nom_caissier")
    nomCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.nom_caissier.toString))
    val prenomCol = new TableColumn[Caissier, String]("prenom_caissier")
    prenomCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.prenom_caissier.toString))
    val usernameCol = new TableColumn[Caissier, String]("username")
    usernameCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.username.toString))
    val passwordCol = new TableColumn[Caissier, String]("password")
    passwordCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.password.toString))
    val villeCol = new TableColumn[Caissier, String]("ville")
    villeCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.ville.toString))

    val telephoneCol = new TableColumn[Caissier, String]("telephone")
    telephoneCol.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.telephone.toString))

    tableCaissier.getColumns().setAll(idCol, idAdminCol, nomCol, prenomCol, usernameCol, passwordCol, villeCol, telephoneCol)
    val datas=loadData()
    //  tableCaissier.getItems(datas)
    tableCaissier.getItems.setAll(datas)
 //   tableCaissier.getItems.addAll(datas)

  }
//  tableCaissier.setEditable(true)




 // case class Caissier(val id_caissier: String, val id_admin: String, val nom_caissier: String, val prenom_caissier: String, val username: String, val password: String, val ville: String, val telephone: Int)






  def loadData(): ObservableList[Caissier] = {
    val connection = DBConnection.getConnection
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT id_caissier, id_admin, nom_caissier, prenom_caissier, username, password, ville, telephone FROM caissier")
    val caissiers: ObservableList[Caissier] = javafx.collections.FXCollections.observableArrayList[Caissier]()

  //  val caissiers = new util.ArrayList[Caissier]()
    while (resultSet.next()) {
      val id_caissier = resultSet.getString("id_caissier")
      val id_admin = resultSet.getString("id_admin")
      val nom_caissier = resultSet.getString("nom_caissier")
      val prenom_caissier = resultSet.getString("prenom_caissier")
      val username = resultSet.getString("username")
      val password = resultSet.getString("password")
      val ville = resultSet.getString("ville")
      val telephone = resultSet.getInt("telephone")
      val caissier = new Caissier(id_caissier, id_admin, nom_caissier, prenom_caissier, username, password, ville, telephone)
      print(caissier.id_caissier,"hhhhhhhhhhhhhhhhhhhhhhhhhh")
      caissiers.add(caissier)
    }
    connection.close()
    return caissiers
  }
}
