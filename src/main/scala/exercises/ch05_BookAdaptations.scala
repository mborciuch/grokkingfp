package exercises

object ch05_BookAdaptations extends App {
  case class Book(title: String, authors: List[String])

  case class Movie(title: String)
  {

    def recommendedBooks(friend: String): List[Book] = {
      val scala   = List(
        Book("FP in Scala", List("Chiusano", "Bjarnason")),
        Book("Get Programming with Scala", List("Sfregola"))
      )
      val fiction = List(
        Book("Harry Potter", List("Rowling")),
        Book("The Lord of the Rings", List("Tolkien"))
      )
      if (friend == "Alice") scala
      else if (friend == "Bob") fiction
      else List.empty
    }

    val friends = List("Alice", "Bob", "Charlie")
    val books   = friends.flatMap(friend => recommendedBooks(friend))
    println(books)

    val authors = books.flatMap(book => book.authors)
    println(authors)

    val chained = friends
      .flatMap(friend => recommendedBooks(friend))
      .flatMap(_.authors)

    println(chained)

  }

  {
    def bookAdaptations(author: String): List[Movie] =
      if (author == "Tolkien")
        List(Movie("An Unexpected Journey"),
          Movie("The Desolation of Smaug"))
      else List.empty

    val books = List(
      Book("FP in Scala", List("Chiusano", "Bjarnason")),
      Book("The Hobbit", List("Tolkien")))
    val movies = books
      .flatMap(book => book.authors.flatMap(author => bookAdaptations(author).map(movie => s"You may like ${movie.title}, " +
        s"because you liked $author's ${book.title}")))

    println(movies)

  }

  {
    case class Point(x: Int, y: Int)

    val result = List(1).map(x =>
      List(-2, 7).map(y =>
        Point(x, y)
      )
    )
    println(result)
  }
}
