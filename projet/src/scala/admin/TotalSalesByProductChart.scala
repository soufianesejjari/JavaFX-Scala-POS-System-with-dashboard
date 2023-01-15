package admin

import javafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, StackedBarChart, XYChart}
import javafx.collections.ObservableList

import java.sql.Connection
class TotalSalesByProductChart {
  val xAxis = new CategoryAxis()
  xAxis.setLabel("Product")
  val yAxis = new NumberAxis()
  yAxis.setLabel("Total Sales")
  val chart = new BarChart[String, Number](xAxis, yAxis)
  chart.setTitle("Total Sales by Product")
  chart.setLegendVisible(false)

  def createChartFromDb(connection: Connection,selectedYear: Int,selectedMonth:String): BarChart[String, Number] = {
    val data = extractData(connection,selectedYear,selectedMonth: String)
    createChart(data)
  }

  private def extractData(connection: Connection,selectedYear: Int,selectedMonth: String): Map[String, Double] = {
    val statement = connection.createStatement()
    var query="SELECT nom_produit, SUM(`total`) as total_sales FROM commande_details o JOIN produit p ON o.id_produit = p.id_produit JOIN commandes c ON o.id_commande = c.id_commande "
    if (selectedYear != 0 && !selectedMonth.equals("tout")) {
      query += "WHERE YEAR(date_commande) = '" + selectedYear + "' AND MONTHName(date_commande) = '" + selectedMonth+"'"
    } else if (selectedYear != 0) {
      query += "WHERE YEAR(date_commande) ='" + selectedYear+"'"
    } else if (!selectedMonth.equals("tout")) {
      query += "WHERE MONTHName(date_commande) = '" + selectedMonth+"'"
    }
    query += " GROUP BY nom_produit"
    var resultSet = statement.executeQuery(query)



  //  if(selectedYear!=0){
  //     resultSet = statement.executeQuery("SELECT nom_produit, SUM(`total`) as total_sales FROM commande_details o JOIN produit p ON o.id_produit = p.id_produit JOIN commandes c ON o.id_commande = c.id_commande WHERE YEAR(date_commande) = '" + selectedYear.toInt +"'  GROUP BY nom_produit")

  //  }
    val data = collection.mutable.Map[String, Double]()
    while (resultSet.next()) {
      val productName = resultSet.getString(1)
      val totalSales = resultSet.getDouble(2)
      data += (productName -> totalSales)
    }
    data.toMap
  }

  private def createChart(data: Map[String, Double]): BarChart[String, Number] = {
    val series = new XYChart.Series[String, Number]
    data.foreach { case (productName, totalSales) =>
      series.getData().add(new XYChart.Data[String, Number](productName, totalSales))
    }
    chart.getData().add(series)
    chart
  }
}
