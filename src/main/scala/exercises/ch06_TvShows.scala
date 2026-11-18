package exercises

object ch06_TvShows extends App {

  case class TvShow(title: String, start: Int, end: Int)

  // Testowe dane (dostępne w OptionVersion i EitherVersion)
  val validShows: List[String] =
    List("Breaking Bad (2008-2013)", "The Wire (2002-2008)", "Chernobyl (2019-2019)")

  val singleYearShows: List[String] =
    List("Chernobyl (2019)", "B (2002)")

  val invalidShows: List[String] =
    List("Breaking Bad (2008-2013)", "Mad Men (-2015)", "(2022)", "A (abc-2010)", "B (2010-xyz)", "Show 2010")

  val mixedShows: List[String] =
    List("The Wire (2002-2008)", "broken", "Chernobyl (2019-2019)")

  val edgeCases: List[String] =
    List("", "()", "(-)", "A (1992-)", "C (-2012)", "(2022)", "Name (2001-2002")

  object OptionVersion {

    def extractYearStart(rawShow: String): Option[Int] = {
      val bracketOpen = rawShow.indexOf('(')
      val dash = rawShow.indexOf('-')
      for {
        yearStr <- if (bracketOpen != -1 && dash > bracketOpen + 1)
          Some(rawShow.substring(bracketOpen + 1, dash))
        else None
        year <- yearStr.toIntOption
      } yield year
    }

    def extractName(rawShow: String): Option[String] = {
      val dash = rawShow.indexOf("(")
      if (dash > 0)
        Some(rawShow.substring(0, dash).trim())
      else None
    }

    def extractYearEnd(rawShow: String): Option[Int] = {
      val dash = rawShow.indexOf('-')
      val bracketClose = rawShow.indexOf(')')
      for {
        yearStr <- if (dash != -1 && bracketClose > dash + 1)
          Some(rawShow.substring(dash + 1, bracketClose))
        else None
        year <- yearStr.toIntOption
      } yield year
    }

    def extractOneYear(rawShow: String): Option[Int] = {
      val dash = rawShow.indexOf("-")
      val bracketOpen = rawShow.indexOf("(")
      val bracketClose = rawShow.indexOf(")")

      for {
        yearStr <- if (dash == -1 && bracketOpen != -1 && bracketOpen + 1 < bracketClose)
          Some(rawShow.substring(bracketOpen + 1, bracketClose))
        else None
        year <- yearStr.toIntOption
      } yield year

    }

    def parseShow(show: String): Option[TvShow] = {
      for {
        name <- extractName(show)
        yearStart <- extractYearStart(show).orElse(extractOneYear(show))
        yearEnd <- extractYearEnd(show).orElse(extractOneYear(show))
      } yield TvShow(name, yearStart, yearEnd)

//      val res1 = extractName(show).map(name => extractYearStart(show)
        
//      val result = extractName(show).map(name => extractYearStart(show).map(ys => extractYearEnd(show).map(ye => extractYearEnd(show))))

    }

    def addOrResign(parsedShows: Option[List[TvShow]], newAddedShow: Option[TvShow]): Option[List[TvShow]] = {
      for {
        shows <- parsedShows
        show <- newAddedShow
      } yield shows.appended(show)
    }
    def extractSingleYearOrYearEnd(show: String): Option[Int] = {
      extractOneYear(show).orElse(extractYearEnd(show))
    }
    def extractAnyYear(show: String): Option[Int] = {
      extractYearStart(show).orElse(extractYearEnd(show)).orElse(extractOneYear(show))
    }

    def extractSingleYearIfNameExists(show: String): Option[Int] = {
      extractName(show).flatMap(name => extractOneYear(show))
    }

    def extractAnyYearIfNameExists(show: String): Option[Int] = {
        extractName(show).flatMap(name => extractAnyYear(show))
    }

    def printResult(show: String): String  = {
      println("-----------------------------------")
      println("Show:" + show)
      println("extractSingleYearOrYearEnd: " + extractSingleYearOrYearEnd(show))
      println("extractAnyYear: "  + extractAnyYear(show))
      println("extractSingleYearIfNameExists: " + extractSingleYearIfNameExists(show))
      println("extractAnyYearIfNameExists: "  + extractAnyYearIfNameExists(show))
      show
    }

//    printResult("A (1992-)")
//    printResult("B (2002)")
//    printResult("C (-2012)")
//    printResult("(2022)")

    def addOrResignDemo(): Unit = {

      println(addOrResign(Some(List.empty), Some(TvShow("Chernobyl", 2019, 2019))))
      println(addOrResign(Some(List(TvShow("Chernobyl", 2019, 2019))),
        Some(TvShow("The Wire", 2002, 2008))))
      println(addOrResign(Some(List(TvShow("Chernobyl", 2019, 2019))), None))
      println(addOrResign(None, Some(TvShow("Chernobyl", 2019, 2019))))
      println(addOrResign(None, None))

    }

    def parseShows(rawShows: List[String]): Option[List[TvShow]] = {

      rawShows.map(parseShow).foldLeft(Some(List.empty))(addOrResign)

    }

  }



  object EitherVersion {

    def extractYearStart(rawShow: String): Either[String, Int] = {
//      val bracketOpen = rawShow.indexOf('(')
//      val dash = rawShow.indexOf('-')
//      val either = if (bracketOpen != -1 && dash > bracketOpen + 1)
//        Right(rawShow.substring(bracketOpen + 1, dash))
//      else Left(s"Can't extract start year from $rawShow")
//
//      either.flatMap(yearString => yearString.toIntOption.toRight(s"Can't parse year ${yearString}"))

      val bracketOpen = rawShow.indexOf('(')
      val dash = rawShow.indexOf('-')

      for {
        yearString <- if (bracketOpen != -1 && dash > bracketOpen + 1)
          Right(rawShow.substring(bracketOpen + 1, dash))
        else Left(s"Can't extract start year from $rawShow")
        mapped <- yearString.toIntOption.toRight(s"Can't parse year ${yearString}")
      } yield mapped

    }

    def extractName(rawShow: String): Either[String, String] = {
      val dash = rawShow.indexOf("(")
      if (dash > 0)
        Right(rawShow.substring(0, dash).trim())
      else Left(s"Cannot extract name from ${rawShow}")
    }

    def extractYearEnd(rawShow: String): Either[String, Int] = {
      val dash = rawShow.indexOf('-')
      val bracketClose = rawShow.indexOf(')')
      for {
        yearStr <- if (dash != -1 && bracketClose > dash + 1)
          Right(rawShow.substring(dash + 1, bracketClose))
        else Left(s"Can't extract end year from $rawShow")
        mapped <- yearStr.toIntOption.toRight(s"Can't parse year ${yearStr}")
      } yield mapped
    }

    def extractOneYear(rawShow: String): Either[String, Int] = {
      val dash = rawShow.indexOf("-")
      val bracketOpen = rawShow.indexOf("(")
      val bracketClose = rawShow.indexOf(")")

      for {
        yearStr <- if (dash == -1 && bracketOpen != -1 && bracketOpen + 1 < bracketClose)
          Right(rawShow.substring(bracketOpen + 1, bracketClose))
        else Left(s"Can't extract single year from $rawShow")
        year <- yearStr.toIntOption.toRight(s"Can't parse year ${yearStr}")
      } yield year

    }


    def addOrResign(parsedShows: Either[String, List[TvShow]], newAddedShow: Either[String, TvShow]): Either[String, List[TvShow]] = {
      for {
        shows <- parsedShows
        show <- newAddedShow
      } yield shows.appended(show)
    }


    def parseShow(show: String): Either[String, TvShow] = {

      val nameEither = extractName(show)
      val yearStartEither = nameEither.flatMap(name => extractYearStart(show))

      for {
        name <- extractName(show)
        yearStart <- extractYearStart(show).orElse(extractOneYear(show))
        yearEnd <- extractYearEnd(show).orElse(extractOneYear(show))
      } yield TvShow(name, yearStart, yearEnd)

    }



    def parseShows(rawShows: List[String]): Either[String, List[TvShow]] = {

      rawShows.map(parseShow).foldLeft(Right(List.empty))(addOrResign)

    }

  }

  validShows.foreach(show => println(EitherVersion.extractName(show)))
  invalidShows.foreach(show => println(EitherVersion.extractName(show)))

  

}
