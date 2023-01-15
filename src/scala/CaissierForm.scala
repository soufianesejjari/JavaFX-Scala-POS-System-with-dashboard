import baseDonne.{DBConnection, Product}
import com.sun.corba.se.impl.util.Utility.printStackTrace
import javafx.application.Platform
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.FXCollections.observableList
import javafx.collections.{FXCollections, ObservableList}
import javafx.collections.transformation.{FilteredList, SortedList}
import javafx.event.{ActionEvent, EventHandler}
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.image.Image
import javafx.scene.input.{MouseButton, MouseEvent}
import javafx.scene.{Node, Parent, Scene}
import javafx.stage.{Modality, Stage, StageStyle}
import javafx.util.Callback
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage, PDPageContentStream}
import org.apache.pdfbox.pdmodel.font.PDType1Font
import saission.CaissierSaission

import java.util.Date
import java.awt.Desktop
import java.io.File
import java.net.URL
import java.nio.file.Paths
import java.sql.{Connection, PreparedStatement, ResultSet, Statement}
import java.util.ResourceBundle
import scala.+:
import scala.collection.JavaConverters.*
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters.*
import scala.jdk.CollectionConverters.*

class CaissierForm extends Initializable {

  val productsDetail: ObservableList[Product] = javafx.collections.FXCollections.observableArrayList[Product]()
  var fruitList = List(Product(0, "0", 0))


  var productName, quantity, price, orderNumber: String = null
  @FXML var productNameField: TextField = _
  @FXML var quantityField: TextField = _
  @FXML var priceField: TextField = _
  @FXML var orderNumberField: TextField = _


  @FXML var productTableView: TableView[Fruit] = _


  @FXML var tableProduitVente: TableView[tableProd] = _
  //les collone de cet table
  /**
  @FXML var produitColumn: TableColumn[tableProd, String] = _

  @FXML var prixColumn: TableColumn[tableProd, Int] = _
  @FXML var quantityColumn: TableColumn[tableProd, Int] = _
  @FXML var totalColumn: TableColumn[tableProd, Int] = _
**/
  @FXML var caissierName: Label = _
  @FXML var orderEnregister: Label = _

  @FXML var searchField = null

  @FXML var nomProduit: TextField = _

  @FXML var prixProduit: TextField = _
  @FXML var codeProduit: TextField = _

  @FXML var quantiteProduit: TextField = _


  @FXML var totaleTtc: TextField = _
  @FXML var totaleHT : TextField = _
  @FXML var tauxTva  : TextField = _

  @FXML var searchButton:TextField = _

  @FXML var removeButton :Button = _
  @FXML var addButton    :Button = _
  @FXML var resetButton  :Button = _
  @FXML var paymentButton  :Button = _


  @FXML var quantityLabel = null

  var priceValues: Int = 0
  var quantiteTotale: Int = 0
  var ttcTotale: Int = 0



  @FXML def addProduct(): Unit = {
    print("faaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    val product = tableProd(
      codeProduit.getText.toInt,
      nomProduit.getText,
      prixProduit.getText.toInt,
      quantiteProduit.getText.toInt,
      prixProduit.getText.toInt * quantiteProduit.getText.toInt
    )
    tableProduitVente.getItems.add(product)
    nomProduit.setText("")
    prixProduit.setText("")
    quantiteProduit.setText("")
    codeProduit.setText("")


    calculeTotale()
  }



  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    caissierName.setText(CaissierSaission.userName.get)


    val produitId: TableColumn[tableProd, String] = new TableColumn("Code Produit")
    produitId.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.produitId.toString))

    val produitColumn: TableColumn[tableProd, String] = new TableColumn("produit")
    produitColumn.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.produitColumn))

    val quantityColumn: TableColumn[tableProd, String] = new TableColumn("quantity")
    quantityColumn.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.quantityColumn.toString))

    val prixColumn: TableColumn[tableProd, String] = new TableColumn("Prix")
    prixColumn.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.prixColumn.toString))

    val totalColumn: TableColumn[tableProd, String] = new TableColumn("totale")
    totalColumn.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.totalColumn.toString))

    tableProduitVente.getColumns.add(produitColumn)
    tableProduitVente.getColumns.add(quantityColumn)
    tableProduitVente.getColumns.add(prixColumn)
    tableProduitVente.getColumns.add(totalColumn)



    val productNames = getProduct()


    val fruitColumn: TableColumn[Fruit, String] = new TableColumn("Produits")
    fruitColumn.setCellValueFactory(cellData => new SimpleStringProperty(cellData.getValue.fruitName))

    productTableView.getColumns.add(fruitColumn)

    //  productTableView.getItems.addAll(new Fruit("Batata"), new Fruit("Banana"))

    productTableView.getItems.addAll(productNames)
    // productNames.foreach(name=>  productTableView.getItems.add(new Fruit(name)))

    productTableView.setRowFactory(new Callback[TableView[Fruit], TableRow[Fruit]]() {
      override def call(tableView: TableView[Fruit]): TableRow[Fruit] = {
        val row = new TableRow[Fruit]()
        row.setOnMouseClicked(new EventHandler[MouseEvent]() {
          override def handle(event: MouseEvent): Unit = {
            if (event.getButton == MouseButton.PRIMARY && event.getClickCount == 2) {
              val selectedFruit: Fruit = row.getItem
              // Affichez les détails du fruit sélectionné ici
              print("sssssssssssssssssssssssss", selectedFruit)
              getInfoProduct(selectedFruit.fruitName)
            }
          }
        })
        row
      }
    })
    codeProduit.setOnAction(new EventHandler[ActionEvent] {
      override def handle(event: ActionEvent): Unit = {
        val text: String = codeProduit.getText
        val seulProd = productsDetail.filtered(_.id.toString == text)
    if (seulProd.size()==0) {

      val alert = new Alert(AlertType.WARNING)
      alert.setTitle("pas de produit")
      alert.setHeaderText("pas de produit dans la base donnes avec ce code")
     // alert.setContentText("Êtes-vous sûr de vouloir supprimer toutes les lignes de la table ?")

      alert
    }
        else {
        nomProduit.setText(seulProd.get(0).name)
      prixProduit.setText(seulProd.get(0).prix.toString)
      codeProduit.setText(seulProd.get(0).id.toString)
      quantiteProduit.setText("1")
    }

      }
    })

    searchButton.textProperty().addListener((observable, oldValue, newValue) => {
      // mettre à jour la liste observable en fonction des modifications apportées au champ de recherche
      updateObservableList(newValue)
    })
    removeButton.setOnAction((event: ActionEvent) => {
      deleteSelectedRow()
    })
    resetButton.setOnAction((event: ActionEvent) => {
      deleteAllRows()
    })
    paymentButton.setOnAction((event: ActionEvent) => {
      insertOrder()
      deleteAllRows()
      orderEnregister.setDisable(false)
      Thread.sleep(1000) // Délai de 2 secondes
       orderEnregister.setDisable(true)


    })
  }
  def deleteAllRows(): Unit ={


    tableProduitVente.getItems().clear()
    nomProduit.setText("")
    prixProduit.setText("")
    codeProduit.setText("")
    quantiteProduit.setText("")
    totaleTtc.setText("")
    totaleHT.setText("")
    tauxTva.setText("")
  }
  def deleteSelectedRow(): Unit ={
    val selectedIndex = tableProduitVente.getSelectionModel().getSelectedIndex()
    if (selectedIndex >= 0) {
      val selectedItem = tableProduitVente.getSelectionModel().getSelectedItem()
      getInfoProduct(selectedItem.produitColumn)
      productTableView.getItems().remove(selectedIndex)
    } else {
      // Affiche un message d'erreur si aucune ligne n'est sélectionnée
      println("Aucune ligne sélectionnée")
    }
  }
  def updateObservableList(searchText: String): Unit = {
    val obserProd=getProduct()
    // filtrer la liste de produits en fonction du texte de recherche
    val filteredList = obserProd.filtered(product => product.fruitName.contains(searchText))

    // mettre à jour la liste observable avec les éléments filtrés
 //   observableList.setAll(filteredList)
    // Appliquer le filtre à la TableView
    productTableView.setItems(filteredList)

    // Mettre à jour la TableView
    productTableView.refresh()
  }
  def calculeTotale(): Unit ={
  tableProduitVente.getItems.forEach(ligne=>{

    priceValues=priceValues+ligne.prixColumn.toInt
    quantiteTotale=quantiteTotale+ligne.quantityColumn
    ttcTotale=ttcTotale+ligne.totalColumn

  })
  totaleTtc.setText(ttcTotale.toString)
  totaleHT.setText((ttcTotale*0.8).toString)
  tauxTva.setText((ttcTotale*0.2).toString)



}
  def getProduct(): ObservableList[Fruit] = {
    val connection = DBConnection.getConnection
    val statement = connection.createStatement()
    val resultSet: ResultSet = statement.executeQuery("SELECT * FROM produit")
    print("on a ici")
    val products: ObservableList[Fruit] = javafx.collections.FXCollections.observableArrayList[Fruit]()

    // var products =observableList(Seq.empty[Product].asJava)

    while (resultSet.next()) {
      val id = resultSet.getInt("id_produit")
      val name = resultSet.getString("nom_produit")
      val prix = resultSet.getInt("prix")
      print(name, prix, id, "teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeets")
      val product = new Fruit(name)
      val productd = new Product(id, name, prix)
      productsDetail.add(productd)
      products.add(product)
      fruitList = productd +: fruitList

      //  products.addAll(product)

    }


    return products
  }
  def getInfoProduct(nameProduit: String): Unit = {
    val seulProd = productsDetail.filtered(_.name == nameProduit)


    nomProduit.setText(seulProd.get(0).name)
    prixProduit.setText(seulProd.get(0).prix.toString)
    codeProduit.setText(seulProd.get(0).id.toString)
    quantiteProduit.setText("1")


  }

  def insertRows(idOrder: Int,conn:Connection):Unit= {

    val items = tableProduitVente.getItems()
       items.forEach(item=>{
       //  print("laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n",idOrder,item.quantityColumn,item.produitId,item.totalColumn)
         val insertSQL = "INSERT INTO commande_details (id_commande,id_produit,quantite, total) VALUES (?,?,?,?)"
         val pstmt = conn.prepareStatement(insertSQL)
         pstmt.setString(1, idOrder.toString)
         pstmt.setString(2, item.produitId.toString )
         pstmt.setInt(3, item.quantityColumn)
         pstmt.setInt(4, item.totalColumn)

         pstmt.executeUpdate()
       })


  }

  def insertOrder(): Int = {
    val currentDate = new Date()
    var orderId: Int = 0

    try {
      // Ouvrez une connexion à votre base de données
      val conn=DBConnection.getConnection

      val idCaissier=CaissierSaission.userId.get
     // print("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",idCaissier)
      // Créez une requête préparée pour insérer un order dans la table "orders"
      val insertOrderStmt: PreparedStatement = conn.prepareStatement("INSERT INTO commandes (id_caissier, date_commande, totalePrix) VALUES (?, ?, ?)")
      insertOrderStmt.setString(1, idCaissier.toString)
      insertOrderStmt.setDate(2, new java.sql.Date(currentDate.getTime))

      insertOrderStmt.setInt(3, totaleTtc.getText.toInt)

      // Exécutez la requête et obtenez le nombre de lignes affectées
      val affectedRows: Int = insertOrderStmt.executeUpdate()

      // Si une ligne a été affectée, cela signifie que l'insertion a réussi
      if (affectedRows == 1) {
        // Obtenez l'ID de l'order inséré en exécutant une requête de sélection
        val selectOrderIdStmt: PreparedStatement = conn.prepareStatement("SELECT LAST_INSERT_ID()")
        val resultSet: ResultSet = selectOrderIdStmt.executeQuery()

        // Si un résultat a été retourné, cela signifie que l'ID de l'order a été obtenu
        if (resultSet.next()) {
          orderId = resultSet.getInt(1)
          insertRows(orderId,conn)
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }

    // Retournez l'ID de l'order inséré
    orderId
  }
  class Fruit(val fruitName: String)


  case class tableProd(produitId:Int, produitColumn: String, prixColumn: Int, quantityColumn: Int, totalColumn: Int)
}

