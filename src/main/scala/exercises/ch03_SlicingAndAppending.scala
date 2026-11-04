package exercises

object ch03_SlicingAndAppending  extends App {

  def firstTwo(input: List[String]): List[String] = {
    input.slice(0, 2)
  }

  def lastTwo(input: List[String]): List[String] = {
    input.slice(input.length - 2, input.length)
  }


  def movedFirstTwoToTheEnd(input: List[String]): List[String] = {
    val firstTwo = input.slice(0, 2)
    val lastTwo = input.slice(input.length - 2, input.length);
    lastTwo.appendedAll(firstTwo);
  }

  {
    assert(firstTwo(List("A", "B", "C", "D")) == List("A", "B"))
    assert(lastTwo(List("A", "B", "C", "D")) == List("C", "D"))
    assert(movedFirstTwoToTheEnd(List("A", "B", "C", "D")) == List("C", "D", "A", "B"))
  }
}
