package game

import model._
import view._
import controller._

object Main {

  def main(args: Array[String]): Unit = {

    val model = new Model
    val view = new View
    val controller = new Controller(model, view)
    view.init(controller, model)
  }
}