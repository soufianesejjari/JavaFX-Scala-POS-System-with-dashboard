import java.io.File
import java.nio.file.Paths
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font

import java.awt.Desktop

class InvoiceGenerator {
  def generateInvoice(invoiceNumber: String, customerName: String,
                      items: Seq[(String, Double, Int)], total: Double): Unit = {

    val document = new PDDocument()
    val page = new PDPage()
    document.addPage(page)

    val font = PDType1Font.HELVETICA_BOLD
    val fontSize = 12
    val lineHeight = 1.5f * fontSize

    val contentStream = new PDPageContentStream(document, page)
    contentStream.beginText()
    contentStream.setFont(font, fontSize)
    contentStream.newLineAtOffset(50, 750)

    contentStream.showText("Facture n° " + invoiceNumber)
    contentStream.newLineAtOffset(0, -lineHeight)
    contentStream.showText("Client : " + customerName)
    contentStream.newLineAtOffset(0, -lineHeight)
    contentStream.showText("---------------------------------------")
    contentStream.newLineAtOffset(0, -lineHeight)

    items.foreach { case (name, price, quantity) =>
      contentStream.showText(s"$name   x$quantity   ${price * quantity}€")
      contentStream.newLineAtOffset(0, -lineHeight)
    }

    contentStream.showText("---------------------------------------")
    contentStream.newLineAtOffset(0, -lineHeight)
    contentStream.showText("Total : " + total + "€")

    contentStream.endText()
    contentStream.close()

    val fileName = s"invoice-$invoiceNumber.pdf"
    val file = new File(fileName)
    document.save(file)
    document.close()

    println(s"La facture a été enregistrée dans $fileName")
    Desktop.getDesktop.open(file)
  }
}
