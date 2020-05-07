package tdd

import org.scalatest.FunSpec
import org.scalatest.Matchers
import model._
import controller._

class Menu_test extends FunSpec with Matchers  {
  describe("Game Simulation"){
    describe("Menu"){
      it("advances the order of players"){
        val expectedResult_1 = "Player 1, Player 2, Player 3, Player 4"
        val expectedResult_2 = "Player 2, Player 3, Player 4, Player 1"
        val expectedResult_3 = "Player 3, Player 4, Player 1, Player 2"
        val expectedResult_4 = "Player 4, Player 1, Player 2, Player 3"

        Menu.showPlayerOrder should be(expectedResult_1)
        Menu.advancePlayerOrder 
        Menu.showPlayerOrder should be(expectedResult_2)
        Menu.advancePlayerOrder 
        Menu.showPlayerOrder should be(expectedResult_3) 
        Menu.advancePlayerOrder 
        Menu.showPlayerOrder should be(expectedResult_4)
        Menu.advancePlayerOrder
      }
      
      it("show initial Player Order"){
        val expectedResult = "Player 1, Player 2, Player 3, Player 4"
        Menu.showPlayerOrder should be(expectedResult)
      }
                                      //Spade(S) Diamond(D) Club(C) Heart(H)
      it("Should show the game area"){ //Jack(j) Queen(Q) King(K) Ace(A) Others(#)
        val expectedResult = 
        "MAKE YOUR MOVE!\n"+
        "Player 1" +
        "Player 2" +
        "Player 3" +
        "Player 4" 

          /// THis is the same as initialize game 
      PlayerOrder.showHands should be(expectedResult)
      //PlayerOrder.resetHands
      }
      it("Should initialize the game"){
        val expectedResult = 
        "MAKE YOUR MOVE!\n"+
        "Player 1: (AD,KD,QD,JD,10D,9D,8D)," +
        "Player 2: (7D,6D,5D,4D,3D,2D,AD)," +
        "Player 3: (KC,QC,JC,10C,9C,8C,7C)," +
        "Player 4: (6C,5C,4C,3C,2C,AC,KH)" 
        //controller.init(1)
        Menu.initialize
        GameArea.show should equal(expectedResult)
        PlayerOrder.resetHands
      } 

    it("can check for a winner"){
        GameArea.give
        Menu.winner should equal(("?",false))
        PlayerOrder.resetHands
    }
        it("should discard card from and put it in deck"){
          val expectedResult = 
            "MAKE YOUR MOVE!\n"+
        "Player 1: ("/*AD*/+"KD,QD,JD,10D,9D,8D)," +
        "Player 2: (7D,6D,5D,4D,3D,2D,AD)," +
        "Player 3: (KC,QC,JC,10C,9C,8C,7C)," +
        "Player 4: (6C,5C,4C,3C,2C,AC,KH)" 
            GameArea.give
            Menu.doMove
            GameArea.show should be (expectedResult)
            PlayerOrder.resetHands
        }

      it ("should make a full turn"){
        val expectedResult = 
          "MAKE YOUR MOVE!\n"+
        "Player 1: ("/*AD*/+"KD,QD,JD,10D,9D,8D)," +
        "Player 2: ("/*7D*/+"6D,5D,4D,3D,2D,AD)," +
        "Player 3: (KC,QC,JC,10C"/*,9C,8C,*/+",7C)," +
        "Player 4: ("/*6C,*/+"5C,4C,3C,2C,AC,KH)"  
          GameArea.give         
          Menu.doTurn
          GameArea.show should be (expectedResult)
          PlayerOrder.resetHands
      }
      it ("should finish game"){
        val expectedResult = 
          "The winner is Player 4"  
          GameArea.give        
          Menu.doGame should be(expectedResult)
          PlayerOrder.resetHands
      }
      it ("should assign strategies to specified players"){
        val expectedResult = 
          "MAKE YOUR MOVE!\n"+
        "Player 1: ("/*AD*/+"KD,QD,JD,10D,9D,8D)," +
        "Player 2: ("/*7D*/+"6D,5D,4D,3D,2D,AD)," +
        "Player 3: (KC,QC,JC,10C"/*,9C,8C,*/+",7C)," +
        "Player 4: ("/*6C,*/+"5C,4C,3C,2C,AC,KH)"
        GameArea.give
        Menu.set
        Menu.doTurn
        GameArea.show should be (expectedResult)
        PlayerOrder.resetHands
      }
      it ("should show strategies of each player"){
              val expectedResult = 
                "Strategies----------\n"+
                "Player 1: Only Discard," +
                "Player 2: Only Groups," +
                "Player 3: Only Sequences," +
                "Player 4: Best Of"
                Menu.set          
                Menu.showStrategies should be(expectedResult)
            }

    }

  }
 
  
}