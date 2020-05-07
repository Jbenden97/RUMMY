package model

class Strategy(x:Int) {
  private var help = x
  private var empty= ""
  if (help == 1) empty = "1 meld"
  if (help == 2) empty = "largest group"
  if (help == 3) empty = "always toss"
  if (help == 4) empty = "whatever"
  def getName : String= empty
}