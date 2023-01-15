package admin
import javafx.scene.chart.{CategoryAxis, NumberAxis, StackedBarChart}
import javafx.collections.ObservableList
import javafx.scene.chart.XYChart

import java.sql.Connection
class StackChart {
  val xAxis = new CategoryAxis()
  xAxis.setLabel("Period")
  val yAxis = new NumberAxis()
  yAxis.setLabel("Total Sales")
  val chart = new StackedBarChart[String, Number](xAxis, yAxis)
  chart.setTitle("Total Sales by Category and Date")

  def createChartFromDb(connection: Connection,selectedYear:Int ): StackedBarChart[String, Number] = {
    val data = extractData(connection,selectedYear)
    createChart(data)
  }

  private def extractData(connection: Connection,selectedYear:Int ): Map[(String, String), Double] = {
    val statement = connection.createStatement()
    var resultSet = statement.executeQuery(
      "SELECT monthName(`date_commande`) as date, `categorie_produit` as category, SUM(`total`) as sales FROM commandes o JOIN commande_details od ON o.id_commande = od.id_commande JOIN produit p ON od.`id_produit` = p.`id_produit`   GROUP BY DATE(date), category")

    if(selectedYear!=0){
       resultSet = statement.executeQuery(
        "SELECT monthName(`date_commande`) as date, `categorie_produit` as category, SUM(`total`) as sales FROM commandes o JOIN commande_details od ON o.id_commande = od.id_commande JOIN produit p ON od.`id_produit` = p.`id_produit` WHERE YEAR(date_commande) = '" + selectedYear.toInt +"'  GROUP BY DATE(date), category")

    }
     val data = collection.mutable.Map[(String, String), Double]()
    while (resultSet.next()) {
      val category = resultSet.getString(2)
      val period = resultSet.getString(1)
      val sales = resultSet.getDouble(3)
      data += ((category, period) -> sales)
    }
    data.toMap
  }

  private def createChart(data: Map[(String, String), Double]): StackedBarChart[String, Number] = {
    
    val categories = data.keys.map(_._1).toSet
    val periods = data.keys.map(_._2).toSet
    for(category <- categories){
      val series = new XYChart.Series[String, Number]()
      series.setName(category)
      for(period <- periods){
        val sales = data.getOrElse((category, period), 0.0)
        series.getData.add(new XYChart.Data[String, Number](period, sales))
      }
      chart.getData.add(series)
    }
    chart
  }
}





