import admin.DashbordAdmin
import baseDonne.DBConnection
import com.sun.javafx.scene.text.TextLine
import eu.hansolo.tilesfx.{Tile, TileBuilder}
import eu.hansolo.tilesfx.fonts.Fonts
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.geometry.Insets
import javafx.scene.chart.*
import javafx.scene.control.{Button, TextField}
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.{Parent, Scene, control}
import javafx.stage.Stage
import javafx.scene.control.Label

import java.io.IOException
import java.net.URL
import java.sql.{Connection, ResultSet, Statement}
import java.util.ResourceBundle
import scala.collection.mutable.ListBuffer
import scala.util.control.Exception
class AdminClass extends Application {


  val xAxis = new CategoryAxis()
  val yAxis = new NumberAxis()
  @FXML
  private var lineCharte: AnchorPane = _

  val lineChart = new LineChart[String, Number](xAxis, yAxis)


  @FXML
  private var pnItems : VBox = _
  @FXML
  private var btnOverview :Button= _

  @FXML
  private var btnOrders :Button= _

  @FXML
  private var btnCustomers :Button= _

  @FXML
  private var btnMenus :Button=_


  @FXML
  private var btnSignout :Button = _

  @FXML
  private var pnlCustomer : Pane= _

  @FXML
  private var pnlOrders : Pane= _

  @FXML
  private var pnlOverview : Pane= _

  @FXML
  private var BorderOverview : BorderPane= _

  @FXML
  private var pnlMenus :Pane= _
  override def start(primaryStage: Stage): Unit = {

  val loader = new FXMLLoader(getClass.getResource("dashbord_admin.fxml"))
    val root = loader.load[Parent]()

    val scene: Scene = new Scene(root)

  try{
    val d =MainClass.getClass.getResource("/sample.fxml")
  val view :Pane= new FXMLLoader().load(d.openStream())

  val pnlOverviews = loader.getNamespace().get("pnlOverview").asInstanceOf[Pane]

  pnlOverviews.getChildren().add(view)

}catch{
  case e: IOException => {
    print("An error occurred while loading the fxml file: " + e.getMessage())
    e.printStackTrace()
  }
}
    // produits
    try{
      val d =MainClass.getClass.getResource("/produit_traitement.fxml")
      val view :Pane= new FXMLLoader().load(d.openStream())

      val pnlOverviews = loader.getNamespace().get("pnlCustomer").asInstanceOf[Pane]

      pnlOverviews.getChildren().add(view)

    }catch{
      case e: IOException => {
        print("An error occurred while loading the fxml file: " + e.getMessage())
        e.printStackTrace()
      }
    }
    // caissier
    try{
      val d =MainClass.getClass.getResource("/caisser_Traitement.fxml")
      val view :Pane= new FXMLLoader().load(d.openStream())

      val pnlOverviews = loader.getNamespace().get("pnlMenus").asInstanceOf[Pane]

      pnlOverviews.getChildren().add(view)

    }catch{
      case e: IOException => {
        print("An error occurred while loading the fxml file: " + e.getMessage())
        e.printStackTrace()
      }
    }
    // orders
    try{
      val d =MainClass.getClass.getResource("/commade_traitement.fxml")
      val view :Pane= new FXMLLoader().load(d.openStream())

      val pnlOverviews = loader.getNamespace().get("pnlOrders").asInstanceOf[Pane]

      pnlOverviews.getChildren().add(view)

    }catch{
      case e: IOException => {
        print("An error occurred while loading the fxml file: " + e.getMessage())
        e.printStackTrace()
      }
    }


    primaryStage.setScene(scene)
    primaryStage.show()
  // primaryStage.setScene(new Scene(root))
  // primaryStage.show()
  }
  def getChart(): Unit ={
  val connection = DBConnection.getConnection


  val dates = getListsFromDb(connection)._2
  val nombres = getListsFromDb(connection)._1

  // Créer la série de données
  val series = new XYChart.Series[String, Number]
  series.setName("Nombre d'orders par date")

  // Ajouter les données à la série
  for (i <- dates.indices) {
    series.getData.add(new XYChart.Data[String, Number](dates(i), nombres(i)))
    print("date user test",dates(i),nombres(i))
  }

  // Ajouter la série au diagramme de lignes
  lineChart.getData.add(series)

  // Ajouter le diagramme de lignes à la scène
  // lineChart.setPrefSize(800, 600)

  lineCharte.getChildren.add(lineChart)
}


  def getListsFromDb(cnx: Connection): (List[Int], List[String]) = {
    // Créez les listes mutables qui vont contenir les données récupérées
    val ordersByDate = ListBuffer[Int]()
    val dates = ListBuffer[String]()

    // Récupérez une connexion à la base de données à partir de l'instance de DbConnection
    val connection: Connection = cnx

    try {
      // Créez une requête SQL qui sélectionne le nombre d'ordres par date
      val query = "SELECT COUNT(*) as num_orders, date_commande FROM commandes GROUP BY date_commande"
      val statement: Statement = connection.createStatement
      val resultSet: ResultSet = statement.executeQuery(query)

      // Parcourez le résultat de la requête et ajoutez les valeurs de num_orders et date aux listes mutables
      while (resultSet.next()) {
        ordersByDate += resultSet.getInt("num_orders")
        dates += resultSet.getString("date_commande")
      }
    } finally {
      // N'oubliez pas de fermer la connexion à la base de données lorsque vous avez fini de travailler avec
    //  connection.close()
    }

    // Convertissez les listes mutables en listes immuables et renvoyez-les en tant que tuple
    (ordersByDate.toList, dates.toList)
  }

  def handleClicks(actionEvent: ActionEvent): Unit = {
    if (actionEvent.getSource eq btnCustomers) {
      pnlCustomer.setStyle("-fx-background-color : #1620A1")
      pnlCustomer.toFront()

    }
    if (actionEvent.getSource eq btnMenus) {
      pnlMenus.setStyle("-fx-background-color : #53639F")
      pnlMenus.toFront()
    }
    if (actionEvent.getSource eq btnOverview) {
//      pnlOverview.setStyle("-fx-background-color : #fff")
      pnlOverview.toFront()
  // val loader = new FXMLLoader(getClass.getResource("../../fxml/sample.fxml"))
  //    val fragmentRoot = loader.load[Parent]()
//
//
  //    BorderOverview.getChildren().add(fragmentRoot)

      // Add the second page to the specific VBox
    //  vbox.getChildren().add(otherRoot)
     // vbox.getChildren.set(0,otherRoot)

    }
    if (actionEvent.getSource eq btnOrders) {
      pnlOrders.setStyle("-fx-background-color : #464F67")
      pnlOrders.toFront()


    }
    if (actionEvent.getSource eq btnSignout) {
      print("yeeeeeeeeeeeeeeeeeeeeeeeeee")
      val stage = lineCharte.getScene.getWindow.asInstanceOf[Stage]
      val fxmlLoader = new FXMLLoader(getClass.getResource("login_caisse_or_admin.fxml"))
      val root = fxmlLoader.load[Parent]
      val scene = new Scene(root)
      //stage.setMaximized(true)


      //   stage.setFullScreen(true)
      //   stage.setFullScreenExitHint("")  //empty string
      stage.setScene(scene)

    }
  }



}