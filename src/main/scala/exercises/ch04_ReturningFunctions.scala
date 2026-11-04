package exercises

object ch04_ReturningFunctions extends App {
  {
//    def greaterThan(list: List[Int]): Int => List[Int] = {
//      than => list.filter(i => i > than)
//    }
//    val greaterThanBase = greaterThan(List(5, 1, 2, 4, 0))
//    val greaterThan4 =  greaterThanBase(4)
//    assert(greaterThan4 == List(5));
//
//    val greaterThan1 = greaterThanBase(1)
//    assert(greaterThan1 == List(5, 2, 4));
//
//    def divisibleBy(list: List[Int]): Int => List[Int] = {
//      than => list.filter(i => i % than == 0)
//    }

    def greaterThan(than: Int): Int => Boolean = {
      i => i > than
    }
    assert(List(5, 1, 2, 4, 0).filter(greaterThan(4)) == List(5))

    def divisibleBy(by: Int): Int => Boolean = {
      i => i % by == 0
    }
    assert(List(5, 1, 2, 4, 15).filter(divisibleBy(5)) == List(5, 15))
  }

  {
    def greaterThan(than: Int)(i: Int): Boolean = {
      i > than
    }

    def greaterThanv2(than: Int): Int => Boolean = {
     i => i > than
    }

    List(5, 1, 2, 4, 0).filter(greaterThan(4))
    List(5, 1, 2, 4, 0).filter(greaterThanv2(4))

    def divisibleBy(number: Int)(i: Int): Boolean = {
      i % number == 0
    }

    println(List(5, 1, 2, 4, 15).filter(divisibleBy(5)))
  }

  {
    val foldLeft1Result = List(5, 1, 2, 4, 100).foldLeft(0)((total, value) => total + value)
    assert(foldLeft1Result == 112)

    val foldLeft2Result = List("scala", "rust", "ada").foldLeft(0)((total, value) => total + value.length)
    assert(foldLeft2Result == 12)

    def numberOfS(s: String): Int = {
      s.length - s.replaceAll("s", "").length
    }

    val foldLeft3Result = List("scala", "haskell", "rust", "ada").foldLeft(0)((total, value) => total + numberOfS(value))
    assert(foldLeft3Result == 3)

    val fold4LeftResult = List(5, 1, 2, 4, 15).foldLeft(Int.MinValue)((maximum, value) => Math.max(maximum, value))
    assert(fold4LeftResult == 15)
  }
  
  
}


