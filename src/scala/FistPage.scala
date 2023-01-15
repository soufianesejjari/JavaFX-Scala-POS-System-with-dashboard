import com.sun.corba.se.impl.util.Utility.printStackTrace
import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.Button
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout.AnchorPane
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import java.net.URL
import java.util.ResourceBundle


class FistPage extends Initializable {

var stg:Stage=_

  def start(stage: Stage): Unit = {
    stage.setTitle("managmentSystem")
    stage.setMinWidth(600)
    stage.setMinHeight(400)

    val root: Parent = FXMLLoader.load(getClass.getResource("MainSetup.fxml"))
    val scene: Scene = new Scene(root)
    stage.setScene(scene)
    stage.show()
     stg=stage
  }

  @FXML
  private var buttonAdmin: Button = _
  @FXML
  private var buttonCaissier: Button = _


  @FXML
  private var imageView: ImageView = _
  @FXML
  private var anchorPane: AnchorPane = _

  def showImage(): Unit = {
    try {
      imageView.setFitWidth(stg.getWidth)
      imageView.setFitHeight(stg.getHeight)

      val image = new Image("img/bgMain.png")
      imageView.setImage(image)
      imageView.setCache(true)
    } catch {
      case e: Exception =>
        printStackTrace
    }
  }
  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    /**  val button: Button = new Button
    button.setText("Click me!")
    button.setOnAction(_ => println("Button clicked!"))

     */
  anchorPane.setStyle("-fx-background-image: url('/img/vgMain.png');")
    showImage()
    buttonAdmin.setOnAction(_ => versAdmin())
    buttonCaissier.setOnAction(_ => versCaissier())

  }
  def versCaissier(): Unit ={

    val fxmlLoader = new FXMLLoader(getClass.getResource("login_caisse_or_admin.fxml"))
    val root = fxmlLoader.load[Parent]
    val scene = new Scene(root)
    //stage.setMaximized(true)

    val stage = anchorPane.getScene.getWindow.asInstanceOf[Stage]

    //   stage.setFullScreen(true)
    //   stage.setFullScreenExitHint("")  //empty string
    stage.setScene(scene)
//
//
//    val stage = anchorPane.getScene.getWindow.asInstanceOf[Stage]
//
//
// val adminClass=new AdminClass()
//
//   adminClass.start(stage)

  }
  def versAdmin(): Unit ={
    // Chargez la deuxième page lorsque le bouton est cliqué
    val fxmlLoader = new FXMLLoader(getClass.getResource("login_caisse_or_admin.fxml"))
    val root = fxmlLoader.load[Parent]
    val scene = new Scene(root)
    //stage.setMaximized(true)

    val stage = anchorPane.getScene.getWindow.asInstanceOf[Stage]

 //   stage.setFullScreen(true)
 //   stage.setFullScreenExitHint("")  //empty string
    stage.setScene(scene)


  }


}