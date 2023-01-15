package admin.caissier

import admin.caissier.Caissier

import baseDonne.CaissierDAO
import javafx.scene.control.{ButtonType, Dialog, Label, TextField}
import javafx.scene.layout.GridPane
class CaissierEditorDialog(caissier: Caissier,typee:Int) extends Dialog[ButtonType] {
  setTitle("Edit Caissier")
  setHeaderText("Edit the details of the selected caissier")

  private val idAdminField = new TextField(caissier.id_admin.toString)
  private val nomField = new TextField(caissier.nom_caissier)
  private val prenomField = new TextField(caissier.prenom_caissier)
  private val usernameField = new TextField(caissier.username)
  private val passwordField = new TextField(caissier.password)
  private val villeField = new TextField(caissier.ville)
  private val telephoneField = new TextField(caissier.telephone.toString)

  private val form = new GridPane
  form.add(new Label("id_admin"), 0, 0)
  form.add(idAdminField, 1, 0)
  form.add(new Label("nom_caissier"), 0, 1)
  form.add(nomField, 1, 1)
  form.add(new Label("prenom_caissier"), 0, 2)
  form.add(prenomField, 1, 2)
  form.add(new Label("username"), 0, 3)
  form.add(usernameField, 1, 3)
  form.add(new Label("password"), 0, 4)
  form.add(passwordField, 1, 4)
  form.add(new Label("ville"), 0, 5)
  form.add(villeField, 1, 5)
  form.add(new Label("telephone"), 0, 6)
  form.add(telephoneField, 1, 6)

  getDialogPane().setContent(form)
  val buttons = getDialogPane().getButtonTypes()
  buttons.add(ButtonType.OK)
  buttons.add(ButtonType.CANCEL)
  setResultConverter(dialogButton => {
    if (dialogButton == ButtonType.OK) {
      val eleme:Caissier=Caissier(id_caissier = caissier.id_caissier,idAdminField.getText(),
        nomField.getText(),prenomField.getText(),
        usernameField.getText(),passwordField.getText(),
        villeField.getText(),telephoneField.getText().toInt
      )

  //   caissier.id_admin = idAdminField.getText().toInt
  //   caissier.nom_caissier = nomField.getText()
  //   caissier.prenom_caissier = prenomField.getText()
  //   caissier.username = usernameField.getText()
  //   caissier.password = passwordField.getText()
  //   caissier.ville = villeField.getText()
  //   caissier.telephone = telephoneField.getText()
      //Persist the changes to the database
      if(typee==0){
        CaissierDAO.updateCaissier(eleme)

      }
      else{
        CaissierDAO.addCaissier(eleme)

      }
    }
    dialogButton
  })
}
