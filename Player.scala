package model
import scala.io.StdIn.readLine
class Player(var s:String){
	//private val name: String = s
	// var hand = drawHand
	private var hand: List[Card] = List.empty
	def winStatus(player:Player):(String,Boolean) = {
    //println(player.hand.size)
    if (player.hand.size == 0)(s,true) else ("?",false)
  }
	var handSize:Int = hand.size
  var strat:String = "Best Of"
  def setStrat:Unit = {
    if (this.s== "Player 1") strat = "Only Discard"
    else if (this.s == "Player 2") strat = "Only Groups"
    else if (this.s == "Player 3") strat = "Only Sequences"
    else strat = "Best Of"
  }
	def dealCard(card:Card): Unit = {
		hand = card :: hand
	}

  def show: String = {
  	var handStr = hand.foldLeft(this.getName + ": ("){
  		(str, card) => {
  			str + card.display + ","
  		}
  	}.trim.stripSuffix(",") + ")"
  	handStr 
  }
  def dumpHand: Unit = {
    hand = List.empty
  }

	def discard(card:Card) = {
		hand = hand.filter(c => c.value != card.value || c.suit != card.suit)
		//if (hand.isEmpty) winStatus = (name,true)
	}


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
      discard(new Card(v,s))
    }
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
  // def setStrategies : Unit = {
  //   for (i <- 1 to 4) {
  //     getPlayer(i).setStrategy(new Strategy(i))
  //   }
  // }
  
  // def showStrategies : String = {
  //   "Player 1:\n" +
  //   p1.showStrategy + "\n" +
  //   "Player 2:\n" +
  //   p2.showStrategy + "\n" +
  //   "Player 3:\n" +
  //   p3.showStrategy + "\n" +
  //   "Player 4:\n" +
  //   p4.showStrategy + "\n" +
  //   "\n"
  // }

  // def makeMove(player:Player):(String,Boolean) = {
  // 	???
  	/*******
  	Move Functions
  	*******/

  	/*def srtMeld(cards:Array[Card]):String = {
  		GameArea.addToMelds(cards)
  		for (card <- cards){
  			//GameArea.addToDeck(c)
  			hand = hand.filter(c => c.value != card.value&& c.suit != card.suit)
  		}
  		if (hand.isEmpty) winStatus = (name,true)
  		PlayerOrder.advance
  	}*/
  	/*def layOff(meldInd:Int,cards:Array[Card]):String = {
  		GameArea.addToMeld(meldInd,cards)
  		for (card <- cards){
  			//GameArea.addToDeck(c)
  			hand = hand.filter(c => c.value != card.value&& c.suit != card.suit)
  		}
  		if (hand.isEmpty) winStatus = (name,true)
  		PlayerOrder.advance
  	}*/
  	/*******
  	Move Decisions
  	*******/
    
  // 	if (player.hand.size == 7 && player.s == "Player 1"){
  // 		player.discard(new Card("A","D"))
  // 	} 
  // 	else if (player.hand.size == 7 && player.n == "Player 2"){
  // 		player.discard(new Card("A","C"))
  // 		player.discard(new Card("3","C"))
  // 		player.discard(new Card("6","C"))
  // 	}
  // 	else if (player.hand.size == 7 && player.name == "Player 3"){
  // 		player.discard(new Card("5","D"))
  // 	}
  // 	else if (player.hand.size == 7 && player.name == "Player 4"){
  // 		player.discard(new Card("2","D"))
  // 		player.discard(new Card("6","D"))
  // 		player.discard(new Card("7","D"))
  // 	}
  // 	else if (player.hand.size < 7 && player.name == "Player 1"){
  // 		player.discard(new Card("5","C"))
  // 		player.discard(new Card("2","C"))
  // 		player.discard(new Card("4","S"))
  // 	}
  // 	else if (player.hand.size < 7 && player.name == "Player 2"){
  // 		player.discard(new Card("K","C"))
  // 		player.discard(new Card("8","C"))
  // 		player.discard(new Card("8","D"))
  // 		player.discard(new Card("8","H"))
  // 	}
  // 	else if (player.hand.size < 7 && player.name == "Player 3"){
  // 		player.discard(new Card("J","C"))
  // 		player.discard(new Card("4","C"))
  // 	}
  // 	else if(player.hand.size < 7 && player.name == "player4"){
  // 		player.discard(new Card("A","H"))
  // 		player.discard(new Card("3","H"))
  // 		player.discard(new Card("K","H"))
  // 	}
  //   //println((player.name,player.hand.size))
  //   //println(winStatus(player))
  //   winStatus(player)

  // } 

	def getName: String = s

	// def getHand: String = {
	//   var h = ""
	//   for(i <- hand){
	//     h = (hand(i) + ",")
	//   }
	//   return h
	// }

	// def dealCard(card:Card):Unit ={
	// 	hand = card :: hand
	// }
}