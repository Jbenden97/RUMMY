package model
object GameArea{
  private var deck = new Deck()
  private var melds:Array[Array[Card]] = Array()
  private var players = Array(
  	PlayerOrder(0),
  	PlayerOrder(1),
  	PlayerOrder(2),
  	PlayerOrder(3)
  )
  def addToDeck(card:Card):Unit = deck.addToNcard(card)
  def addToMelds(cards:Array[Card]):Unit = melds = melds :+ cards
  def addToMeld(index:Int,cards:Array[Card]):Unit = {
    for (c<-cards) melds(index-1) = melds(index-1):+c
  }
  def draw = deck.draw
  var ready = false

  def give: Unit ={ 

    players(0).dealCard(new Card("8","D"))
    players(0).dealCard(new Card("9","D"))
    players(0).dealCard(new Card("10","D"))
    players(0).dealCard(new Card("J","D"))
    players(0).dealCard(new Card("Q","D"))
    players(0).dealCard(new Card("K","D"))
    players(0).dealCard(new Card("A","D")) 
     players(1).dealCard(new Card("A","D")) 
    players(1).dealCard(new Card("2","D"))
    players(1).dealCard(new Card("3","D"))
    players(1).dealCard(new Card("4","D"))
    players(1).dealCard(new Card("5","D"))
    players(1).dealCard(new Card("6","D"))
    players(1).dealCard(new Card("7","D"))
     players(2).dealCard(new Card("7","C"))
    players(2).dealCard(new Card("8","C"))
    players(2).dealCard(new Card("9","C"))
    players(2).dealCard(new Card("10","C"))
    players(2).dealCard(new Card("J","C"))
    players(2).dealCard(new Card("Q","C"))
    players(2).dealCard(new Card("K","C"))
     players(3).dealCard(new Card("K","H"))
    players(3).dealCard(new Card("A","C"))
    players(3).dealCard(new Card("2","C"))
    players(3).dealCard(new Card("3","C"))
    players(3).dealCard(new Card("4","C"))
    players(3).dealCard(new Card("5","C"))
    players(3).dealCard(new Card("6","C"))


    //this.ready= true
    
  }

  def resetArea:Unit = {
    players(0).dumpHand
    players(1).dumpHand
    players(2).dumpHand
    players(3).dumpHand
  }


  def show:String = {
    val intro = "MAKE YOUR MOVE!\n"
    //if(ready== false)give
    intro+players.foldLeft(""){
      (str, player) => str + player.show + ","
    }.trim.stripSuffix(",")
    // Cdt Sean moriarty's show on his git helped me create 
    //this i need help with my show functions for this and 
    //playerorder his syntax helped me get it.
  	//val p1Hand = PlayerOrder.Player1.hand.



    // val board = 

    //   "Player 1: " + PlayerOrder.Player1.hand + "\n" + 
    //   "Player 2: " + PlayerOrder.Player2.hand + "\n" + 
    //   "Player 3: " + PlayerOrder.Player3.hand + "\n" + 
    //   "Player 4: " + PlayerOrder.Player4.hand + "\n" + 
    //   "---------------- \n"

    //     return board
  }

  // def hand: String ={
  // 	var p1hand = "(A/D,A/H,A/S,2/C,2/D,2/H,2/S)"
  // 	var p2hand = "(3/C,3/D,3/H,3/S,4/C,4/D,4/H"
  // 	var p3hand = "(4/S,5/C,5/D,5/H,5/S,6/C,6/D)
  // 	var p4hand = (6/H,6/S,7/C,7/D,7/H,7/S,8/C)

  //}
  
  
}