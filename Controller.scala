package controller
import model._
import view._
//import Menu._



//******* CONTROLLER *******
class Controller(val model: Model, val view: View) {

  def showCard {
    model.Deck.flipCard
    model.Deck.flippedTop match {
      case Some(card) => view.flippedCard.changeCard(card)
      case None => view.hiddenDeck.showAsEmpty
    }
    model.Deck.hiddenTop match {
      case Some(card) => view.hiddenDeck.showAsHidden
      case None => view.hiddenDeck.showAsEmpty
    }
    
    view.update
  }
  
  def shuffleDeck {
    model.Deck.shuffle
  }

  def takeCard(num:Int) {
    model.PlayerOrder.takeCard_currentPlayer
    model.Deck.flippedTop match {
      case Some(card) => view.flippedCard.changeCard(card)
      case None => view.flippedCard.showAsEmpty
    }
    val currentPlayer = num//model.PlayerOrder.getCurrentPlayerNumber - 1
    model.PlayerOrder.showHand_currentPlayer match {
      case Some(cards) => view.playerHands(currentPlayer).showCards(cards)
      case None => view.playerHands(currentPlayer).showAsEmpty
    }
    
    view.update
  }    
  
  def reset {
    model.reset
    view.flippedCard.showAsEmpty
    view.hiddenDeck.showAsHidden
    view.playerHands.reset
    view.update
  }

 // def checkForWinner {
 //    var result = myModel.checkForWinner
 //    if (result != "none") result += " wins!"
 //    myView.updateAnnouncement(result)
 //    myView.update
 //  }
def init(n:Int){
  showCard
  takeCard(n)
  GameArea.give
  // for (p <- 0 to 3){
  //   for(i <- 0 to 6){
  //   showCard
  //   takeCard
  // }
  // model.PlayerOrder.advance
  // }

}
def doMove(x:Int):Unit ={
  model.PlayerOrder(x).discard(0)
  val currentPlayer = x
     model.PlayerOrder.showHand_currentPlayer match {
        case Some(cards) => view.playerHands(currentPlayer).showCards(cards)
        case None => view.playerHands(currentPlayer).showAsEmpty
      }
      PlayerOrder.advance
      view.update
      // this.showCard
      // this.takeCard(x)

  }
  
def doTurn{
    this.doMove(0)
    PlayerOrder.advance
    this.doMove(1)
    PlayerOrder.advance
    this.doMove(2)
   PlayerOrder.advance
    this.doMove(3)
    PlayerOrder.advance

}
def doGame{
  model.doGame
  view.update
}
def showStrat{
  println(model.showStrategies)
}
def winner{
  GameArea.give
  println(model.winner)
}

}