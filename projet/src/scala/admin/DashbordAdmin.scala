package admin
import baseDonne.DBConnection
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.geometry.Orientation
import javafx.scene.Parent
import javafx.scene.chart.{CategoryAxis, LineChart, NumberAxis, PieChart, StackedBarChart, XYChart}
import javafx.scene.control.{ChoiceBox, ComboBox, Label, ListView, PasswordField, TextField}
import javafx.scene.layout.{AnchorPane, HBox, Pane, VBox}
import javafx.scene.paint.Color

import java.net.URL
import java.sql.{Connection, ResultSet, Statement}
import java.util.ResourceBundle
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class DashbordAdmin extends Initializable {
// container fileld

  @FXML
  private var pnlOverview1: Pane = _

  @FXML
  private var profite: Label = _
  @FXML
  private var orders:  Label= _
  @FXML
  private var produits:Label = _
  @FXML
  private var caissier:Label = _
  @FXML
  private var input:   Label = _


  @FXML
  private var firstChart:  VBox= _
  @FXML
  private var secondChart: VBox = _
  @FXML
  private var barChart: VBox = _
  @FXML
  private var barProd: VBox = _

  @FXML
  private var areaDay: VBox = _

  //nav
  @FXML
  private var navBox: HBox = _
  //line chart 1
  val xAxis = new CategoryAxis()
  val yAxis = new NumberAxis()


  val lineChart = new LineChart[String, Number](xAxis, yAxis)
//Stak CHart








  def extractData(selectedYear:Int,selectedMonth:String): Data = {
    val connection = DBConnection.getConnection

    try {
      val statement = connection.createStatement()

      // Extraction of total sales
      val totalSales = extractTotalSales(statement,selectedYear:Int,selectedMonth:String)

      // Extraction of total number of orders
      val totalOrders = extractTotalOrders(statement,selectedYear:Int,selectedMonth:String)

      // Extraction of total number of cashiers
      val totalCashiers = extractTotalCashiers(statement,selectedYear:Int)

      // Extraction of total profit
      val totalProduit = extractTotalProduit(statement,selectedYear)
      val aujouduitRevenu = extractAUjourduitRevenu(statement,selectedYear)


      Data(totalSales, totalOrders, totalCashiers, totalProduit,aujouduitRevenu)
    } finally {
      connection.close()
    }
  }

  private def extractTotalSales(statement: Statement,selectedYear:Int,selectedMonth:String): Double = {
    var query="SELECT SUM(totalePrix) FROM commandes "
    if (selectedYear != 0 && !selectedMonth.equals("tout")) {
      query += "WHERE YEAR(date_commande) = '" + selectedYear + "' AND MONTHName(date_commande) = '" + selectedMonth+"'"
    } else if (selectedYear != 0) {
      query += "WHERE YEAR(date_commande) ='" + selectedYear+"'"
    } else if (!selectedMonth.equals("tout")) {
      query += "WHERE MONTHName(date_commande) = '" + selectedMonth+"'"
    }
    var resultSet = statement.executeQuery(query)

  //  if(selectedYear!=0){
  //     resultSet = statement.executeQuery("SELECT SUM(totalePrix) FROM commandes WHERE YEAR(date_commande) = '" + selectedYear.toInt +"' ")
//
  //  }
    if (resultSet.next()) resultSet.getDouble(1) else 0.0
  }

  private def extractTotalOrders(statement: Statement,selectedYear:Int,selectedMonth:String): Int = {

    var query="SELECT COUNT(*) FROM commandes "
    if (selectedYear != 0 && !selectedMonth.equals("tout")) {
      query += "WHERE YEAR(date_commande) = '" + selectedYear + "' AND MONTHName(date_commande) = '" + selectedMonth+"'"
    } else if (selectedYear != 0) {
      query += "WHERE YEAR(date_commande) ='" + selectedYear+"'"
    } else if (!selectedMonth.equals("tout")) {
      query += "WHERE MONTHName(date_commande) = '" + selectedMonth+"'"
    }
    var resultSet = statement.executeQuery(query)
  //  if(selectedYear!=0){
  //    resultSet = statement.executeQuery("SELECT COUNT(*) FROM commandes WHERE YEAR(date_commande) = '" + selectedYear.toInt +"'")
  //  }
    if (resultSet.next()) resultSet.getInt(1) else 0
  }

  private def extractTotalCashiers(statement: Statement,selectedYear:Int): Int = {
    val resultSet = statement.executeQuery("SELECT COUNT(*) FROM caissier")
    if (resultSet.next()) resultSet.getInt(1) else 0
  }

  private def extractTotalProduit(statement: Statement,selectedYear:Int): Double = {
    val resultSet = statement.executeQuery("SELECT count(*) FROM produit")
    if (resultSet.next()) resultSet.getDouble(1) else 0.0
  }
  private def extractAUjourduitRevenu(statement: Statement,selectedYear:Int): Double = {
    val resultSet = statement.executeQuery("SELECT SUM(totalePrix) FROM commandes WHERE date_commande in (SELECT DATE( NOW() ))")
    if (resultSet.next()) resultSet.getDouble(1) else 0.0
  }



  def createChart(selectedYear:Int,selectedMonth:String): PieChart = {
    val connection = DBConnection.getConnection
    try {
      val statement = connection.createStatement()
      val data = extractDataPie(statement,selectedYear:Int,selectedMonth:String)
      val chart = new PieChart(data)
      chart.setTitle("Ventes totales par caissier")
      chart.setLabelLineLength(50)
      chart.setLabelsVisible(true)
      chart
    } finally {
      connection.close()
    }
  }


  private def extractDataPie(statement: Statement,selectedYear:Int,selectedMonth:String): ObservableList[PieChart.Data] = {
    val data = FXCollections.observableArrayList[PieChart.Data]()
    var query="SELECT caissier.nom_caissier, SUM(commandes.totalePrix) FROM commandes JOIN caissier ON commandes.id_caissier = caissier.id_caissier "
    if (selectedYear != 0 && !selectedMonth.equals("tout")) {
      query += "WHERE YEAR(date_commande) = '" + selectedYear + "' AND MONTHName(date_commande) = '" + selectedMonth+"'"
    } else if (selectedYear != 0) {
      query += "WHERE YEAR(date_commande) ='" + selectedYear+"'"
    } else if (!selectedMonth.equals("tout")) {
      query += "WHERE MONTHName(date_commande) = '" + selectedMonth+"'"
    }
    query +=" GROUP BY caissier.nom_caissier"
    var resultSet = statement.executeQuery(query)

  //  if(selectedYear!=0){
  //     resultSet = statement.executeQuery("SELECT caissier.nom_caissier, SUM(commandes.totalePrix) FROM commandes JOIN caissier ON commandes.id_caissier = caissier.id_caissier WHERE YEAR(date_commande) = '" + selectedYear.toInt +"'  GROUP BY caissier.nom_caissier")
// d
  //  }
    while (resultSet.next()) {
      val cashierName = resultSet.getString(1)
      val sales = resultSet.getDouble(2)
      data.add(new PieChart.Data(cashierName, sales))
    }
    data
  }


  def getChart(selectedYear:Int): LineChart[String, Number] ={
    val connection = DBConnection.getConnection

    val datass=getListsFromDb(connection,selectedYear:Int)
    val dates = datass._2
    val nombres =datass._1

    // Créer la série de données
    val series = new XYChart.Series[String, Number]
    series.setName("Nombre d'orders par date")

    // Ajouter les données à la série
    for (i <- dates.indices) {
      series.getData.add(new XYChart.Data[String, Number](dates(i), nombres(i)))

      print("date user test",dates(i),nombres(i))
    }


  lineChart.setBackground(null)
    // Ajouter la série au diagramme de lignes
    lineChart.getData.add(series)

    // Ajouter le diagramme de lignes à la scène
    // lineChart.setPrefSize(800, 600)
    series.getNode().setStyle("-fx-stroke: blue; -fx-stroke-width: 2px;")
    xAxis.setStyle("-fx-tick-label-fill: black; -fx-tick-mark-fill: black;")
    yAxis.setStyle("-fx-tick-label-fill: black; -fx-tick-mark-fill: black;")
    xAxis.setTickMarkVisible(false)
    yAxis.setTickMarkVisible(false)

  //  series.setCreateSymbols(false)

    lineChart
  }


  def getListsFromDb(cnx: Connection,selectedYear:Int): (List[Int], List[String]) = {
    // Créez les listes mutables qui vont contenir les données récupérées
    val ordersByDate = ListBuffer[Int]()
    val dates = ListBuffer[String]()

    // Récupérez une connexion à la base de données à partir de l'instance de DbConnection
    val connection: Connection = cnx

    try {
      // Créez une requête SQL qui sélectionne le nombre d'ordres par date
      var query = "SELECT    CASE      WHEN MONTH(`date_commande`) <= 4 THEN 'Semistre 1'     WHEN MONTH(`date_commande`) <= 8 THEN 'Semistre 2'    ELSE 'Semistre 3'   END AS semester,  SUM(`totalePrix`) as total_sales FROM commandes   GROUP BY semester"

      if(selectedYear !=0){
         query = "SELECT    CASE      WHEN MONTH(`date_commande`) <= 4 THEN 'Semistre 1'     WHEN MONTH(`date_commande`) <= 8 THEN 'Semistre 2'    ELSE 'Semistre 3'   END AS semester,  SUM(`totalePrix`) as total_sales FROM commandes WHERE YEAR(date_commande) = '" + selectedYear.toInt +"'  GROUP BY semester"

      }
      val statement: Statement = connection.createStatement
      val resultSet: ResultSet = statement.executeQuery(query)

      // Parcourez le résultat de la requête et ajoutez les valeurs de num_orders et date aux listes mutables
      while (resultSet.next()) {
        ordersByDate += resultSet.getInt("total_sales")
        dates += resultSet.getString("semester")
      }
    } finally {
      // N'oubliez pas de fermer la connexion à la base de données lorsque vous avez fini de travailler avec
      //  connection.close()
    }

    // Convertissez les listes mutables en listes immuables et renvoyez-les en tant que tuple
    (ordersByDate.toList, dates.toList)
  }

  private def getYearsFromDb(): mutable.Seq[String] = {
    val connection = DBConnection.getConnection
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT DISTINCT YEAR(`date_commande`) as year FROM commandes ORDER BY year")
    val years = collection.mutable.ArrayBuffer[String]()
    while (resultSet.next()) {
      val year = resultSet.getString(1)
      years += year
    }
    connection.close()
    years
  }
 /** private def getMoisFromDb(): mutable.Seq[String] = {
    val connection = DBConnection.getConnection
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT DISTINCT Month(`date_commande`) as year FROM commandes ORDER BY year")
    val mois = collection.mutable.ArrayBuffer[String]()
    while (resultSet.next()) {
      val unMois = resultSet.getString(1)
      mois += unMois
    }
    connection.close()
    mois
  } **/
  private def updateCharts(selectedYear: Int,selectedMonth:String) :Unit={
    val connection = DBConnection.getConnection
    val totalSalesByProductChart = new TotalSalesByProductChart()
     val newProd=totalSalesByProductChart.createChartFromDb(connection, selectedYear,selectedMonth)
    barProd.getChildren.set(0,newProd)
    val totalSalesByWeekdayChart = new TotalSalesByWeekdayChart
      val updetatedChart=totalSalesByWeekdayChart.createChartFromDb(connection, selectedYear,selectedMonth)
    areaDay.getChildren.set(0,updetatedChart)
   // areaDay.getChildren.add(updetatedChart)
    print("cliiiiiiiiiiiiiiicked")


    //deuxieme barChart
    val barCha= new StackChart()
    val charttt=barCha.createChartFromDb(DBConnection.getConnection,selectedYear)

    barChart.getChildren.set(0,charttt)
    //premier lineChart

    secondChart.getChildren.set(0,getChart(selectedYear))

    //pie chart
    firstChart.getChildren.set(0,createChart(selectedYear,selectedMonth))

    //donnes mis a jour
    val data= extractData(selectedYear,selectedMonth)
    produits.setText(data.totalProduit.toString)
    orders.setText(data.totalOrders.toString)
    input.setText(data.totalSales.toString)
    caissier.setText(data.totalCashiers.toString)
    profite.setText(data.revenuAujourduit.toString)

    // ...
  //  connection.close()
  }
  private def getMonths(): Seq[String] = {
    Seq("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
  }
def yearChoiceFunction():Unit={
  val yearChoice = new ComboBox[String]()

  yearChoice.getItems().add("tout")
  val years = getYearsFromDb()foreach({ u =>
    yearChoice.getItems().add(u)
  })
  yearChoice.getSelectionModel().selectFirst()


 // val monthChoice = new ChoiceBox[String]()
  val monthChoice = new ListView[String]()
  monthChoice.setOrientation(Orientation.HORIZONTAL)
  monthChoice.setPrefWidth(700)

  monthChoice.getItems().add("tout")
  val moiss = getMonths().foreach(m=>{
    monthChoice.getItems().add(m)
  })
 // monthChoice.getItems().addAll(getMonths())
  monthChoice.getSelectionModel().selectFirst()
  // yearChoice.getItems().addAll(years)
  //    yearChoice.setPopupSide(Side.BOTTOM)

  navBox.getChildren.add(yearChoice)



  yearChoice.valueProperty().addListener { (_, _, newValue) =>
    var selectedYear = 0
    if(newValue=="tout"){
      selectedYear = 0
    }
    else {
      selectedYear = newValue.toInt

    }


     val selectedMonth = monthChoice.getSelectionModel.getSelectedItem.toString


    updateCharts(selectedYear, selectedMonth)
  }
  // Add listener to monthChoice to update the charts
  monthChoice.getSelectionModel().selectedItemProperty().addListener { (_, _, newValue) =>
  //  var selectedYear = yearChoice.getValue().toInt
   // var selectedMonth = newValue.toInt

     val selectedMonth = newValue



    var selectedYear =0

    if(yearChoice.getValue()=="tout"){
      selectedYear = 0
    }
    else {
      selectedYear = yearChoice.getValue().toInt

    }
    updateCharts(selectedYear, selectedMonth)
  }
  navBox.getChildren.add(monthChoice)
  /**
  yearChoice.valueProperty().addListener { (_, _, newValue) =>
    var selectedYear = 0
    if(newValue=="tout"){
      selectedYear = 0
    }
    else {
      selectedYear = newValue.toInt

    }

    print("liiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii")
    updateCharts(selectedYear)
  } **/

}

 /** def moisChoiceFunction():Unit={
    val moisChoice = new ComboBox[String]()

    moisChoice.getItems().add("tout")
    val mois = getMoisFromDb()foreach({ u =>
      moisChoice.getItems().add(u)
    })
    // yearChoice.getItems().addAll(years)
    //    yearChoice.setPopupSide(Side.BOTTOM)

    navBox.getChildren.add(moisChoice)

    moisChoice.getSelectionModel().selectFirst()
    moisChoice.valueProperty().addListener { (_, _, newValue) =>
      var selectedMois = 0
      if(newValue=="tout"){
        selectedMois = 0
      }
      else {
        selectedMois = newValue.toInt

      }

      print("liiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii")
      updateChartsMois(selectedMois)
    }

  }**/


 def createView(): Parent = {
   val loader = new FXMLLoader(getClass.getResource("/sample.fxml"))

   val root = pnlOverview1
   root
 }
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    // getChart()
    val data= extractData(0,"tout")
    produits.setText(data.totalProduit.toString)
    orders.setText(data.totalOrders.toString)
    input.setText(data.totalSales.toString)
    caissier.setText(data.totalCashiers.toString)
    profite.setText(data.revenuAujourduit.toString)
    //extrait les information de pie chart 1
    val chart = createChart(0,"tout")
    val colors = List(Color.BLUE, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PURPLE)
    val dataList = chart.getData()
    for (i <- 0 until dataList.size()) {
      val data = dataList.get(i)
      val node = data.getNode()
      val color = colors(i % colors.size)
      node.setStyle("-fx-pie-color: " + color + ";")
    }
    //pie chart
    firstChart.getChildren.add(chart)



    //premier lineChart

    secondChart.getChildren.add(getChart(0))

    //deuxieme barChart
    val barCharG= new StackChart()
   val chartt=barCharG.createChartFromDb(DBConnection.getConnection,0 )

    barChart.getChildren.add(chartt)
    //deuxieme barChart produit
    val barCharP= new TotalSalesByProductChart()
    val chartProd=barCharP.createChartFromDb(DBConnection.getConnection,0,"tout")

    barProd.getChildren.add(chartProd)
    // areaChart DayName
    val barCharDay= new TotalSalesByWeekdayChart()
    val areaChart=barCharDay.createChartFromDb(DBConnection.getConnection,0,"tout")

    areaDay.getChildren.add(areaChart)

    //filtrage par annes
   // val yearChoice = new ChoiceBox[String]()
   yearChoiceFunction()



  }
  case class Data(totalSales:Double,totalOrders:Int,totalCashiers:Int,totalProduit:Double,revenuAujourduit:Double)
}
