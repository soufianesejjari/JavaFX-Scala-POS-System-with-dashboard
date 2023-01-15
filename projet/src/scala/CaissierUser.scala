import javafx.fxml.{FXML, FXMLLoader, Initializable}
import javafx.scene.control.{Button, Label, PasswordField, TextField}
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import java.net.URL
import java.util.ResourceBundle
import java.io.IOException
import baseDonne.DBConnection
import javafx.scene.layout.{AnchorPane, VBox}
import saission.CaissierSaission

import java.sql.{Connection, DriverManager, ResultSet, SQLException}

class CaissierUser extends Initializable {



  override def initialize(location: URL, resources: ResourceBundle): Unit = {
  /**  val button: Button = new Button
    button.setText("Click me!")
    button.setOnAction(_ => println("Button clicked!"))

  */
    wrongLogIn.setText("")




  button.setOnAction(_ =>userLogIn() )
  }

  @FXML
  private var anchorPane: AnchorPane = _

  @FXML
  private var button: Button = _
  @FXML
  private var wrongLogIn: Label = _
  @FXML
  private var username: TextField  = _
  @FXML
  private var password: PasswordField = _

  @throws[IOException]
  def userLogIn(): Unit = {
    checkLogin()
  }

  @throws[IOException]
  private def checkLogin(): Unit = {

/**
    if (username.getText.toString.equals("soufiane") && password.getText.toString.equals("123")) {
      wrongLogIn.setText("Success!")
      println("bravooooooooooooooooooooooooooooo")
    }
    **/
     if (!username.getText.isEmpty || !password.getText.isEmpty) {
       if (DBConnection.isValidCredentialUser(username.getText, password.getText)) {

         CaissierSaission.userId = getCaissierId(username.getText,password.getText)
     // print(CaissierSaission.userId,"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab")


         wrongLogIn.setText("bounjour soufiane !")
         CaissierSaission.userName = Some(username.getText)
         // Chargez la deuxième page lorsque le bouton est cliqué
         val fxmlLoader = new FXMLLoader(getClass.getResource("caissierForm.fxml"))
  //  val fxmlLoader = new FXMLLoader(getClass.getResource("admin_interface.fxml"))
         val root = fxmlLoader.load[Parent]
         val scene = new Scene(root)
         val stage = anchorPane.getScene.getWindow.asInstanceOf[Stage]
         stage.setScene(scene)

       }
       else if(DBConnection.isValidCredentialAdmin(username.getText, password.getText)){
         val stage = anchorPane.getScene.getWindow.asInstanceOf[Stage]
         print("bounjoooooooooooooor")

         val adminClass=new AdminClass()

         adminClass.start(stage)
       }
     } else {
       print("prooooooooooobleeeeeeeeeeeeeem")

       wrongLogIn.setText("svp entre les information.")
     }




}

  def getCaissierId(username: String, password: String): Option[Int] = {
    try {
      val connection = DBConnection.getConnection
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery(s"SELECT * FROM caissier WHERE username='$username' AND password='$password'")
      if (resultSet.next()) Some(resultSet.getInt("id_caissier")) else None
    } catch {
      case e: SQLException => e.printStackTrace()
        None
    }
  }

}