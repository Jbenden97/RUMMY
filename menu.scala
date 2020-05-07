package model

object Menu {

  def showGameArea : String = {
    GameArea.give
    GameArea.show
  }
  
  def showPlayerOrder : String = {
    PlayerOrder.show
  }
  
  def advancePlayerOrder : String = {
    PlayerOrder.advance
    PlayerOrder.show
  }
  
  def initialize : Unit = {
  	GameArea.give

  }
  
  def doMove : Unit = {
  	PlayerOrder(0).choice
  }
  def winner:(String,Boolean) = {
  	var win:(String,Boolean) = ("?",false)
  	for (plind <- 0 to 3) {
  		win = PlayerOrder(plind).winStatus(PlayerOrder(plind))
  	}
  	win
  }
  def doTurn : (String,Boolean) = {
    //println("in")
    var winner = ("?",false)
    var posWinner = ("?",false)
  	for (t <- 0 to 3) {
      Menu.doMove
      Menu.advancePlayerOrder
  		posWinner = PlayerOrder(t).winStatus(PlayerOrder(t))
      if (posWinner!=("?",false)) winner = posWinner
      //println(("posWinner",posWinner))
      //println(("winner",winner))
  	}
    winner
  }
  def doGame:String = {
  	var winner:(String,Boolean) = ("?",false)
  	//while(winner._2 == false){
    while(winner == ("?",false)){
  	   winner = Menu.doTurn
       //println(winner)
    }

  	//}
  	"The winner is " + winner._1
  }
  def set:Unit = {
  	for(plr <- PlayerOrder){
      plr.setStrat
    }
  }
  def showStrategies: String = {
  	PlayerOrder.showStrategies
  }
}




