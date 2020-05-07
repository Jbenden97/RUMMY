package model
object PlayerOrder extends scala.collection.mutable.Queue[Player]{
  var player1 = new Player("Player 1")
  var player2 = new Player("Player 2") 
  var player3 = new Player("Player 3") 
  var player4 = new Player("Player 4") 
  this.enqueue(player1)
  this.enqueue(player2)
  this.enqueue(player3)
  this.enqueue(player4)
  def resetHands:Unit = {
    player1.dumpHand
    player2.dumpHand
    player3.dumpHand
    player4.dumpHand
  }
  /*def fullTurn: (String,Boolean) ={ 
    Menu.doTurn
    Menu.winner
  }*/

  def show: String = {
    return PlayerOrder(0).getName + ", " + PlayerOrder(1).getName + ", " + PlayerOrder(2).getName + ", " + PlayerOrder(3).getName
  }
  def showStrategies: String = {
    val string:String = 
      "Strategies----------\n"+
      player1.getName+": "+player1.strat+","+
      player2.getName+": "+player2.strat+","+
      player3.getName+": "+player3.strat+","+
      player4.getName+": "+player4.strat
    string
  }
  

def advance: Unit = {
    val x = this.dequeue()
    this.enqueue(x)
  }
      def showHands: String = {
      return "MAKE YOUR MOVE!\n"+
      PlayerOrder(0).s + PlayerOrder(0).showHand +  
      PlayerOrder(1).s + PlayerOrder(1).showHand + 
      PlayerOrder(2).s + PlayerOrder(2).showHand +  
      PlayerOrder(3).s + PlayerOrder(3).showHand  

    }
  
}