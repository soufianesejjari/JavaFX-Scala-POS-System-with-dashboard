package admin.orders

import admin.caissier.Caissier
import admin.orders
import admin.produit.Produit
import baseDonne.CaissierDAO
import javafx.event.ActionEvent
import javafx.scene.control.ButtonBar.ButtonData
import javafx.scene.control.{ButtonType, Dialog, Label, TextField}
import javafx.scene.layout.GridPane

class CommandeEditorDialog(commande: CommandeDetails) extends Dialog[ButtonType] {
  val id_produitField = new TextField()
  val quantiteField = new TextField()
  val totalField = new TextField()

  id_produitField.setText(commande.id_produit.toString)
  quantiteField.setText(commande.quantite.toString)
  totalField.setText(commande.total.toString)

  val grid = new GridPane()
  grid.add(new Label("id_produit"), 0, 0)
  grid.add(id_produitField, 1, 0)
  grid.add(new Label("quantite"), 0, 1)
  grid.add(quantiteField, 1, 1)
  grid.add(new Label("total"), 0, 2)
  grid.add(totalField, 1, 2)

  getDialogPane().setContent(grid)

  val saveButtonType = new ButtonType("Save", ButtonData.OK_DONE)
  getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL)

  val saveButton = getDialogPane().lookupButton(saveButtonType)
  saveButton.addEventFilter(ActionEvent.ACTION, (event: ActionEvent) => {
    try {
      val command=CommandeDetails(commande.id_commande_details,id_produitField.getText().toInt,quantiteField.getText().toInt,commande.date_commande,totalField.getText().toDouble,commande.caissier_name)
      ProduitDAO.update(command)

    } catch {
      case e: NumberFormatException =>
        event.consume()
        id_produitField.setText(e.toString)
        id_produitField.setPromptText("Invalid Number")
        quantiteField.setText("")
        quantiteField.setPromptText("Invalid Number")
        totalField.setText("")
        totalField.setPromptText("Invalid Number")
    }
  })

  setResultConverter((dialogButton: ButtonType) => {
    if (dialogButton == saveButtonType) {
      saveButtonType
    } else {
      null
    }
  })
}