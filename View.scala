package view

import model._
import controller._



import scala.swing._
import BorderPanel.Position._
import java.awt.geom.Rectangle2D
import java.awt.geom.Ellipse2D
import java.awt.Color
import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import scala.swing.Orientation





//******* VIEW ******* 
class View {

  var controller: Option[Controller] = None
  var model: Option[Model] = None

  //******* init *******  
  def init(ctr: Controller, mod: Model) {
    controller = Some(ctr)
    model = Some(mod)    
  }  
  
  
  //******* View components ******* 
  
  val buttons = new BoxPanel(Orientation.Horizontal) {
    contents += new Button(Action("Randomize Deck") { 
      controller.get.shuffleDeck
    })
    contents += new Button(Action("Flip Card") { 
      controller.get.showCard
    })
    // contents += new Button(Action("Give Flipped Card to player") { 
    //   controller.get.takeCard
    // })
     contents += new Button(Action("Initialize") { 
      controller.get.reset
      for(i <- 0 to 3){
        for(j <- 0 to 6){
          controller.get.init(i)
        }
        model.get.PlayerOrder.advance
      }
    })
    contents += new Button(Action("Reset") { 
      controller.get.reset
    })
    contents += new Button(Action("Winner?") { 
      controller.get.winner
    })
    contents += new Button(Action("Do Move!") { 
      controller.get.doMove(0)
    })
    contents += new Button(Action("Do Turn") { 
      controller.get.doTurn
    })
    contents += new Button(Action("Do Game!") { 
      controller.get.doGame
    })
    contents += new Button(Action("Strategies?") { 
      controller.get.showStrat
    })

  }


    
  var flippedCard = new CardPanel

  val hiddenDeck = new HiddenCardPanel
  
  val deckSpaces = new BoxPanel(Orientation.Horizontal) {
    contents += hiddenDeck
    contents += flippedCard   
    background = Color.green
    preferredSize = new Dimension(254,198)
  }
  
  val cardSpaces = new BorderPanel {    
    layout += new Label("Player1") -> West      
    layout += new Label("Player2") -> North
    layout += new Label("Player3") -> East
    layout += new Label("Player4") -> South
    layout += deckSpaces -> Center      
  }
  
  val playerHands = new ArrayBuffer[PlayerHandPanel] {

    this += new PlayerHandPanel('v')
    this += new PlayerHandPanel('h')
    this += new PlayerHandPanel('v')
    this += new PlayerHandPanel('h') 
    
    def reset {    
      for (panel <- this) panel.showAsEmpty      
    }
  }

  val south = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(500,150)
    contents += playerHands(3)
    contents += buttons
    opaque = false
  }  
  
  val gameArea = new BorderPanel {
    background = Color.darkGray
    layout += playerHands(0) -> West      
    layout += playerHands(1) -> North
    layout += playerHands(2) -> East
    layout += south -> South
    layout += cardSpaces -> Center      
  }

  //******* MainFrame *******   
  val frame = new MainFrame {
    title = "Card Game"
    contents = gameArea
    centerOnScreen
    visible = true    
  }
  
  //******* update *******   
  def update {
    frame.repaint
    for(panel <- playerHands) panel.repaint
  }

  
  //******* CARDPANEL *******  
  class CardPanel extends Panel {

    var image = javax.imageio.ImageIO.read(new java.io.File("resources/empty.jpg"))
    
    def showAsEmpty {
      image = javax.imageio.ImageIO.read(new java.io.File("resources/empty.jpg"))
      this.repaint
    }
    
    def changeCard(card : Card) {
      image = javax.imageio.ImageIO.read(new java.io.File("resources/" + card.value + card.suit + ".jpg"))
      this.repaint 
    }
    
    override def paint(g: Graphics2D) {
      g.drawImage(image, 18, 48, null)
    }
  }

  //******* HIDDENCARDPANEL *******   
  class HiddenCardPanel extends Panel {

    var image = javax.imageio.ImageIO.read(new java.io.File("resources/back.jpg"))
     
    def showAsEmpty {
      image = javax.imageio.ImageIO.read(new java.io.File("resources/empty.jpg"))
      this.repaint
    }
    
    def showAsHidden {
      image = javax.imageio.ImageIO.read(new java.io.File("resources/back.jpg"))
      this.repaint
    }
    
    override def paint(g: Graphics2D) {
      g.drawImage(image, 54, 48, null)
    }
  }     
  
  //******* PLAYERHANDPANEL ******* 
  class PlayerHandPanel(orientation : Char) extends Panel {

    preferredSize = new Dimension(72,96)

    var images = new ArrayBuffer[BufferedImage]
    images += javax.imageio.ImageIO.read(new java.io.File("resources/empty.jpg"))
    
    def showAsEmpty {
      images.clear
      images += javax.imageio.ImageIO.read(new java.io.File("resources/empty.jpg"))
      this.repaint
    }
    
    def showCards(cards : ArrayBuffer[Card]) {
      images.clear
      for (card <- cards) {
        images += javax.imageio.ImageIO.read(new java.io.File("resources/" + card.value + card.suit + ".jpg"))
      }
      super.repaint 
    }
    
    override def paint(g: Graphics2D) {
      var offset = 36
      for (image <- images) {
        if (orientation == 'v') g.drawImage(image, 0, offset, null)
        else g.drawImage(image, offset+72, 0, null)
        offset += 30
      }      
    }    
  } 
  
}