package baseDonne

case class Product(id: Int, name: String, prix: Int){
  override def toString: String = name

}