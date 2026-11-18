package exercises


import exercises.ch07_MusicArtistsSearch.artistsInitial.{Artist, Genre, Location, YearsActiveEnd, YearsActiveStart}

import scala.Console.println

object ch07_MusicArtistsSearch extends App {

  object artistsInitial {

    opaque type Location = String

    object Location {
      def apply(value: String): Location = value

      extension (a: Location) def name: String = a
    }

    opaque type Genre = String

    object Genre {
      def apply(value: String): Genre = value

      extension (a: Genre) def name: String = a
    }

    opaque type YearsActiveStart = Int

    object YearsActiveStart {
      def apply(value: Int): YearsActiveStart = value

      extension (a: YearsActiveStart) def value: Int = a
    }

    opaque type YearsActiveEnd= Int

    object YearsActiveEnd {
      def apply(value: Int): YearsActiveEnd = value

      extension (a: YearsActiveEnd) def value: Int = a
    }


    case class Artist(
                       name: String,
                       genre: Genre,
                       origin: Location,
                       yearsActiveStart: YearsActiveStart,
                       isActive: Boolean,
                       yearsActiveEnd: YearsActiveEnd
                     )


    case class PeriodInYears(start: Int, end: Option[Int])

    val artists = List(
      Artist("Metallica", "Heavy Metal", "U.S.", 1981, true, 0),
      Artist("Led Zeppelin", "Hard Rock", "England", 1968, false, 1980),
      Artist("Bee Gees", "Pop", "England", 1958, false, 2003)
    )

    def searchArtists(
                       artists: List[Artist],
                       genres: List[String],
                       locations: List[String],
                       searchByActiveYears: Boolean,
                       activeAfter: Int,
                       activeBefore: Int
                     ): List[Artist] = {
      artists.filter(artist =>
        (genres.isEmpty || genres.contains(artist.genre)) &&
          (locations.isEmpty || locations.contains(artist.origin)) &&
          (!searchByActiveYears || (
            (artist.isActive || artist.yearsActiveEnd >= activeAfter) &&
              (artist.yearsActiveStart <= activeBefore)))
      )
      
    }

  }



  def searchArtistsv2(
                     artists: List[Artist],
                     genres: List[String],
                     locations: List[String],
                     searchByActiveYears: Boolean,
                     activeAfter: Int,
                     activeBefore: Int
                   ): List[Artist] =
    artists.filter(artist =>
      (genres.isEmpty || genres.contains(artist.genre.name)) &&
        (locations.isEmpty || locations.contains(artist.origin.name)) &&
        (!searchByActiveYears || (
          (artist.isActive || artist.yearsActiveEnd.value >= activeAfter) &&
            (artist.yearsActiveStart.value <= activeBefore)))
    )

  {
    //Raw primitve
//    val artists = List(
//      Artist("Metallica", "Heavy Metal", "U.S.", 1981, true, 0),
//      Artist("Led Zeppelin", "Hard Rock", "England", 1968, false, 1980),
//      Artist("Bee Gees", "Pop", "England", 1958, false, 2003)
//    )
//    println(artistsInitial.searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022))

    val artists = List(
      Artist("Metallica", Genre("Heavy Metal"), Location("U.S."), YearsActiveStart(1981), true, YearsActiveEnd(0)),
      Artist("Led Zeppelin", Genre("Hard Rock"), Location("England"), YearsActiveStart(1968), false, YearsActiveEnd(1980)),
      Artist("Bee Gees", Genre("Pop"), Location("England"), YearsActiveStart(1958), false, YearsActiveEnd(2003)))
    println(artistsInitial.searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022))
  }


}
