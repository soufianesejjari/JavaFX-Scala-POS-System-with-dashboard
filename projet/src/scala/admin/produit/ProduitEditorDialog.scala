package admin.produit

import admin.caissier.Caissier
import admin.produit.Produit
import baseDonne.CaissierDAO
import javafx.scene.control.{ButtonType, Dialog, Label, TextField}
import javafx.scene.layout.GridPane

class ProduitEditorDialog(produit: Produit , typee:Int) extends Dialog[ButtonType] {
  setTitle("Edit Produit")
  setHeaderText("Edit the details of the selected produit")

  private val idField = new TextField(produit.id_produit.toString)
  private val nomField = new TextField(produit.nom_produit)
  private val categorieField = new TextField(produit.categorie_produit)
  private val prixField = new TextField(produit.prix.toString)

  private val form = new GridPane
  form.add(new Label("id_produit"), 0, 0)
  form.add(idField, 1, 0)
  form.add(new Label("nom_produit"), 0, 1)
  form.add(nomField, 1, 1)
  form.add(new Label("categorie_produit"), 0, 2)
  form.add(categorieField, 1, 2)
  form.add(new Label("prix"), 0, 3)
  form.add(prixField, 1, 3)

  getDialogPane().setContent(form)
  val buttons = getDialogPane().getButtonTypes()
  buttons.add(ButtonType.OK)
  buttons.add(ButtonType.CANCEL)

  setResultConverter(dialogButton => {
    if (dialogButton == ButtonType.OK) {
      produit.id_produit = idField.getText().toInt
      produit.nom_produit = nomField.getText()
      produit.categorie_produit = categorieField.getText()
      produit.prix = prixField.getText().toDouble
      //Persist the changes to the database
      if(typee==0){
        ProduitDAO.updateProduit(produit)

      }
      else{
        ProduitDAO.addProduit(produit)

      }
    }
    dialogButton
  })
}