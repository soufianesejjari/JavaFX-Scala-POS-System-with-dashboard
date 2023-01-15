import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.stage.Stage

import java.io.IOException

class MainClass extends Application {
  override def start(stage: Stage): Unit = {
    val firstPage = new FistPage
    firstPage.start(stage)  }



}


object MainClass {
  def main(args: Array[String]): Unit = {

    Application.launch(classOf[MainClass], args: _*)
  }
}
