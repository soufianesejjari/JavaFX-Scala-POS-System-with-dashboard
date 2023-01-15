package admin

import javafx.scene.chart.{AreaChart, CategoryAxis, NumberAxis, XYChart}

import java.sql.Connection

class TotalSalesByWeekdayChart {
  val xAxis = new CategoryAxis()
  xAxis.setLabel("Weekday")
  val yAxis = new NumberAxis()
  yAxis.setLabel("Total Sales")
  val chart = new AreaChart[String, Number](xAxis, yAxis)
  chart.setTitle("Total Sales by Weekday")
  chart.setLegendVisible(false)

  def createChartFromDb(connection: Connection, selectedYear: Int,selectedMonth:String): AreaChart[String, Number] = {
    val data = extractData(connection, selectedYear,selectedMonth:String)
    createChart(data)
  }

  private def extractData(connection: Connection, selectedYear: Int,selectedMonth:String): Map[String, Double] = {
    val statement = connection.createStatement()
    var query="SELECT DAYNAME(date_commande) as weekday, SUM(`totalePrix`) as total_sales FROM commandes "

    if (selectedYear != 0 && !selectedMonth.equals("tout")) {
      query += "WHERE YEAR(date_commande) = '" + selectedYear + "' AND MONTHName(date_commande) = '" + selectedMonth+"'"
    } else if (selectedYear != 0) {
      query += "WHERE YEAR(date_commande) ='" + selectedYear+"'"
    } else if (!selectedMonth.equals("tout")) {
      query += "WHERE MONTHName(date_commande) = '" + selectedMonth+"'"
    }
    query +="GROUP BY weekday"
    var resultSet = statement.executeQuery(query)

  // if(selectedYear==0){
  //    resultSet = statement.executeQuery("SELECT DAYNAME(date_commande) as weekday, SUM(`totalePrix`) as total_sales FROM commandes GROUP BY weekday")
  // }
  // else{
  //    resultSet = statement.executeQuery("SELECT DAYNAME(date_commande) as weekday, SUM(`totalePrix`) as total_sales FROM commandes WHERE YEAR(date_commande) = '" + selectedYear.toInt +"' GROUP BY weekday")
  // }

    val data = collection.mutable.Map[String, Double]()
    while (resultSet.next()) {
      val weekday = resultSet.getString(1)
      val totalSales = resultSet.getDouble(2)
      data += (weekday -> totalSales)
    }
    data.toMap
  }
  private def createChart(data: Map[String, Double]): AreaChart[String, Number] = {
    val series = new XYChart.Series[String, Number]
    data.foreach { case (weekday, totalSales) =>
      series.getData().add(new XYChart.Data[String, Number](weekday, totalSales))
    }
    chart.getData().add(series)
    chart
  }
}
