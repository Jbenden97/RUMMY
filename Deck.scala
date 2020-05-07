package model
class Deck {
	private val size = 52
	private val value = List("2","3","4","5","6","7","8","9","10","J","Q","K","A")
	private val suit = List ("H","D","C","S")
	private var ncard: List[Card] = (for(s <- suit; v<- value) yield new Card(v,s))
	private var melds:List[List[Card]] = List.empty

	def draw: Card ={
		val card = this.ncard.head
		this.ncard = this.ncard.tail
		card
	}
	def addToNcard(card:Card):Unit = card::ncard
}

	
