package exercises

object ch04_PassingFunctions extends App {

  def rankedScore(score: String => Int, words: List[String]): List[String] = {
    words.sortBy(score).reverse
  }

  def score(s: String): Int = {
    s.replaceAll("a", "").length
  }

  def bonus(s: String): Int = {
    if (s.contains("c")) 5 else 0
  }

  def penalty(s: String): Int = {
    if (s.contains("s")) 7 else 0
  }

  {
    val words = List("ada", "haskell", "scala", "java", "rust")
    val result = rankedScore(w => score(w) + bonus(w) - penalty(w), words)
    println(result)
    assert(result == List("java", "scala", "ada", "haskell", "rust"))

  }

}
