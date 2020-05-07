package model

import scala.collection.mutable.ArrayBuffer


//******* MODEL *******
class Model {
  // object GameArea{

  // }
  def doMove: Unit={
    Menu.doMove
    Menu.advancePlayerOrder
    PlayerOrder(0).discard(1)
  }
    def winner:(String,Boolean) = {
    var win:(String,Boolean) = ("?",false)
    for (plind <- 0 to 3) {
      win = PlayerOrder(plind).winStatus(PlayerOrder(plind))
    }
    win
  }
  def doTurn : (String,Boolean) = {

    var winner = ("?",false)
    var posWinner = ("?",false)
    for (t <- 0 to 3) {
      Menu.doMove
      Menu.advancePlayerOrder
      posWinner = PlayerOrder(t).winStatus(PlayerOrder(t))
      if (posWinner!=("?",false)) winner = posWinner
    }
    winner
  }
    def doGame:String = {
    var winner:(String,Boolean) = ("?",false)
    while(winner == ("?",false)){
       winner = Menu.doTurn
    }
    println("The winner is " + winner._1 +"!!!").toString
  }

    def set:Unit = {
    for(plr <- PlayerOrder){
      plr.setStrat
      }
    }
  def showStrategies: String = {
    PlayerOrder.showStrategies
  }

  def reset {
    Deck.reset
    PlayerOrder.reset
  }
  
  //******* DECK *******
  object Deck extends ArrayBuffer[Card] {
  
    val flippedPile = new ArrayBuffer[Card]
    
    reset
    
    def reset {
      this.clear
      flippedPile.clear
      
      // create all cards
      var cardVal = 2
      var cardSuit = 'D'

      for (i <- 1 to 52) {
        if (i > 12 && i <= 25) cardSuit = 'H'
        if (i > 25 && i <= 38) cardSuit =  'C'
        if (i > 38 && i <= 51) cardSuit =  'D'
        this += new Card(cardVal.toString, cardSuit.toString)
        if ((cardVal + 1) % 15 == 0) { cardVal = 2 } else cardVal += 1
      }      
    }   
    
    def flipCard {
      if (this.length > 0) {
        val card = this.remove(this.length-1)
        flippedPile += card
      }
    }
    
    def takeFlippedCard : Option[Card] = {
      if (flippedPile.length > 0) {
        val card = flippedPile.remove(flippedPile.length-1)
        return Some(card)
      }      
      else return None
    }
    
    def flippedTop = if (flippedPile.length >0) Some(flippedPile.last) else None
    def hiddenTop = if (this.length >0) Some(this.last) else None
    
    def shuffle {
      val shuffled = scala.util.Random.shuffle(this)
      this.clear
      for (card <- shuffled) this += card
    }
    
  }
  //******* PLAYERORDER *******
object PlayerOrder extends scala.collection.mutable.Queue[Player]{
  var player1 = new Player("Player 1")
  var player2 = new Player("Player 2") 
  var player3 = new Player("Player 3") 
  var player4 = new Player("Player 4") 
  this.enqueue(player1)
  this.enqueue(player2)
  this.enqueue(player3)
  this.enqueue(player4)
    
    def getCurrentPlayerNumber = 1
    
    def showHand_currentPlayer : Option[ArrayBuffer[Card]] = {
      if(this(0).buff.length> 0) Some(this(0).buff)
      else if (this(0).buff.length <7){
        this(0).discard(0)
        Some(this(0).buff)
      }
      else None
    }
    
    def takeCard_currentPlayer {
      this(0).takeCard
    }
    
    def reset {
      this(0).reset
      this(1).reset
      this(2).reset
      this(3).reset
      this.clear
      var player1 = new Player("Player 1")
      var player2 = new Player("Player 2") 
      var player3 = new Player("Player 3") 
      var player4 = new Player("Player 4")
      this.enqueue(player1)
      this.enqueue(player2)
      this.enqueue(player3)
      this.enqueue(player4)
    }
    def advance: Unit = {
      val x = this.dequeue()
      this.enqueue(x)
    }
    def show: String = {
      return PlayerOrder(0).s + ", " + PlayerOrder(1).s + ", " + PlayerOrder(2).s + ", " + PlayerOrder(3).s
    }
    def showHands: String = {
      return "MAKE YOUR MOVE!\n"+
      PlayerOrder(0).s + PlayerOrder(0).showHand + "\n" +
      PlayerOrder(1).s + PlayerOrder(1).showHand + "\n" +
      PlayerOrder(2).s + PlayerOrder(2).showHand + "\n" +
      PlayerOrder(3).s + PlayerOrder(3).showHand + "\n" 

    }
    def showStrategies: String = {
    val string:String = 
      "Strategies----------\n"+
      player1.s+": "+player1.setStrat+","+
      player2.s+": "+player2.setStrat+","+
      player3.s+": "+player3.setStrat+","+
      player4.s+": "+player4.setStrat
    string
  }

  }

  //******* PLAYER *******
  class Player(var s:String) {
    var hand: List[Card] = List.empty
    var buff = collection.mutable.ArrayBuffer[Card](hand: _*)
      def reset {
      buff.clear
    }
    
    def takeCard {
      Deck.takeFlippedCard match {
        case Some(card) => buff += card
        case None => {}
      }
    }
    // private var hand: List[Card] = List.empty
  def winStatus(player:Player):(String,Boolean) = {
    //println(player.hand.size)
    if (player.hand.size == 0)(s,true) else ("?",false)
  }
  var handSize:Int = hand.size
  var strat:String = "Best Of"
  def setStrat:Unit = {
    if (this.s == "Player 1") strat = "Only Discard"
    else if (this.s == "Player2") strat = "Only Groups"
    else if (this.s == "Player3") strat = "Only Sequences"
    else strat = "Best Of"
  }
  // def deal: Unit = {
  //   val ncard = this.Deck.draw
  //   hand += card
  // }
   def choice:Unit = {
    //orders hand by suit
    def getPoint(vChar:Char): Int ={
      if(vChar == 'A') 1
      else if (vChar == 'K') 13
      else if (vChar == 'Q') 12
      else if (vChar == 'J') 11
      else vChar.toString.toInt
    }
    val sHand:List[Card] = hand.sortBy(_.suit)
    var string:String = ""
    var set:Set[String] = Set()
    //------------------------
    // seperates sequences by commas identifies biggest sequence
    for (c <- hand) set+=c.suit

    var currSuite:String = sHand(0).suit
    for (c <- sHand) {
      if (currSuite!=c.suit) {
        string = string+"-"+c.value+c.suit+"/"
        currSuite = c.suit
      }
      else string = string+c.value+c.suit+"/"
    }
    val seqArray:Array[Array[String]] = (string.split("-").map(s=>s.split("/").sortBy(s=>getPoint(s(0)))).sortBy(_.size * -1))
    var validMaxSeqArr:Array[String] = Array()
    var currValidSeqArr:Array[String] = Array(seqArray(0)(0))
    for (i <- 1 to seqArray(0).size-1) {  
      var index = i
      if (i == seqArray(0).size) index = i-1
      else index = i
      //println((getPoint(seqArray(0)(index)(0)),getPoint(seqArray(0)(index-1)(0))))
      if ((getPoint(seqArray(0)(index)(0))-getPoint(seqArray(0)(index-1)(0)) == 1)) {
        currValidSeqArr = currValidSeqArr :+ seqArray(0)(index)
      }
      else {
        if (validMaxSeqArr.size < currValidSeqArr.size)
          validMaxSeqArr = currValidSeqArr
        currValidSeqArr :+ seqArray(0)(index)
      }
    }
    // -----------------------

    // gives largest group
    var gStr:String = ""
    val gHand:List[Card] = hand.sortBy(_.value)
    var currVal:String = gHand(0).value
    for (c <- gHand) {
      if (currVal!=c.value) {
        gStr = gStr+"-"+c.value+c.suit+"/"
        currVal = c.value
      }
      else gStr = gStr+c.value+c.suit+"/"
    }
    val gArr:Array[Array[String]] = gStr.split("-").map(s=>s.split("/")).sortBy(_.size * -1)
    // -------------------



    if (this.strat == "Only Discard") actChoice(Array(hand(0).getName.replace("/","")))
    else if (this.strat == "Only Groups") {
      if (gArr(0).size <= 2) actChoice(Array(hand(0).getName.replace("/","")))
      else actChoice(gArr(0))
    }
    else if (this.strat == "Only Sequences") {
      if (validMaxSeqArr.size <= 2) actChoice(Array(hand(0).getName.replace("/","")))
      else actChoice(validMaxSeqArr)
    }
    else /*(this.strat == "Best Of")*/ {
      if (gArr(0).size <=2 && validMaxSeqArr.size<= 2) actChoice(Array(hand(0).getName.replace("/","")))
      else if (gArr(0).size >= validMaxSeqArr.size) actChoice(gArr(0))
      else actChoice(validMaxSeqArr)
    }

    //Check your String
    // (c <- hand) println(c.getName)

  }
  def actChoice(arr:Array[String]):Unit = {
    for (str <- arr){
      val v:String = str(0).toString
      val s:String = str(1).toString
      discards(new Card(v,s))
    }
  }
  
  def show: String = {
    var handStr = hand.foldLeft(this.s + ": ("){
      (str, card) => {
        str + card.display + ","
      }
    }.trim.stripSuffix(",") + ")"
    handStr 
  }
  def dumpHand: Unit = {
    hand = List.empty
  }
def discards(card:Card) = {
    hand = hand.filter(c => c.value != card.value || c.suit != card.suit)
    //if (hand.isEmpty) winStatus = (name,true)
  }
  def discard(x:Int) = {
    if(hand.length >=7){
      val discard =hand(x)
      hand = hand.filter(_!=discard)
      Deck+=discard
    }
    //hand = hand.filter(c => c.value != card.value || c.suit != card.suit)
    //if (hand.isEmpty) winStatus = (name,true)
  }

  def showHand: String = {
    if(hand.length == 0) return ""
    var handStr = ""
    for(i <- hand){
      handStr += (", " + i.getName)
    }
    handStr = handStr.substring(2, handStr.length)
    return handStr
  }


  }

}

class Card(v:String, s:String){
   val value:String = v
   val suit: String = s

  def display: String = value + suit

  def getPoint: Int ={
    if(this.value == "A") 1
    else if (this.value == "K") 13
    else if (this.value == "Q") 12
    else if (this.value == "J") 11
    else this.value.toInt
  }
   
   def getName: String ={
      return v+s
    }
}
