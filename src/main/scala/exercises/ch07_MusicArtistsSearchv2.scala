package exercises

object ch07_MusicArtistsSearchv2 extends App {

  object artistsProductType {

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

    opaque type YearsActiveEnd = Int

    object YearsActiveEnd {
      def apply(value: Int): YearsActiveEnd = value

      extension (a: YearsActiveEnd) def value: Int = a
    }

//    case class Artist(
//                       name: String,
//                       genre: Genre,
//                       origin: Location,
//                       yearsActive: PeriodInYears
//                     )

    enum YearsActive {
      case StillActive(since: Int)
      case ActiveBetween(start: Int, end: Int)
    }

    case class Artist(
        name: String,
        genre: MusicGenre,
        origin: Location,
        yearsActive: YearsActive
    )

    enum MusicGenre {
      case HeavyMetal
      case Pop
      case HardRock
    }

    case class PeriodInYears(start: Int, end: Option[Int])

//    val artists = List(
//      Artist("Metallica", "Heavy Metal", "U.S.", StillActive(1981)),
//      Artist("Led Zeppelin", "Hard Rock", "England", ActiveBetween(1981, 1980)),
//      Artist("Bee Gees", "Pop", "England", ActiveBetween(1958, 2003))
//    )

//    def searchArtists(
//                       artists: List[Artist],
//                       genres: List[String],
//                       locations: List[String],
//                       searchByActiveYears: Boolean,
//                       activeAfter: Int,
//                       activeBefore: Int
//                     ): List[Artist] = {
//      artists.filter(artist =>
//        (genres.isEmpty || genres.contains(artist.genre)) &&
//          (locations.isEmpty || locations.contains(artist.origin)) &&
//          (!searchByActiveYears || (
//            artist.yearsActive. forall(_ >= activeAfter) &&
//              (artist.yearsActive.start <= activeBefore)
//      )))
//
//    }

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
          (!searchByActiveYears || wasArtistActive(artist, activeAfter, activeBefore))
      )

    }

    def wasArtistActive(artist: Artist, activeAfter: Int, activeBefore: Int): Boolean = {
      artist.yearsActive match {
        case YearsActive.StillActive(since)        => since <= activeBefore
        case YearsActive.ActiveBetween(start, end) => end >= activeAfter && start <= activeBefore
      }
    }

    def activeLength(artist: Artist, currentYear: Int): Int = {
      artist.yearsActive match {
        case YearsActive.StillActive(since)        => currentYear - since
        case YearsActive.ActiveBetween(start, end) => end - start
      }
    }
  }

  object artistsModelingBehaviour {

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

    enum MusicGenre {
      case HeavyMetal
      case Pop
      case HardRock
    }

    enum YearsActive {
      case StillActive(since: Int)
      case ActiveBetween(start: Int, end: Int)
    }

    enum SearchCondition {
      case SearchByMusicGenre(genres: List[MusicGenre])
      case SearchByLocation(locations: List[Location])
      case SearchByYearsActive(start: Int, end: Int)
    }

    def wasArtistActive(artist: Artist, activeAfter: Int, activeBefore: Int): Boolean = {
      artist.yearsActive match {
        case YearsActive.StillActive(since)        => since <= activeBefore
        case YearsActive.ActiveBetween(start, end) => end >= activeAfter && start <= activeBefore
      }
    }

    def activeLength(artist: Artist, currentYear: Int): Int = {
      artist.yearsActive match {
        case YearsActive.StillActive(since)        => currentYear - since
        case YearsActive.ActiveBetween(start, end) => end - start
      }
    }

    case class Artist(
        name: String,
        genre: MusicGenre,
        origin: Location,
        yearsActive: YearsActive
    )

    def searchArtists(
        artists: List[Artist],
        searchConditions: List[SearchCondition]
    ): List[Artist] = {
      artists.filter(artist =>
        searchConditions.forall(condition => {
          condition match {
            case SearchCondition.SearchByMusicGenre(genres)      => genres.contains(artist.genre)
            case SearchCondition.SearchByLocation(locations)     => locations.contains(artist.origin)
            case SearchCondition.SearchByYearsActive(start, end) => wasArtistActive(artist, start, end)
          }
        })
      )

    }

  }

  object artistsModelingDesignAndMaintainability {


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

    enum MusicGenre {
      case HeavyMetal
      case Pop
      case HardRock
    }

    case class PeriodInYears(start: Int, end: Int)

    enum YearsActive {
      case StillActive(since: Int, previousPeriods: List[PeriodInYears])
      case ActiveInPeriods(periods: List[PeriodInYears])
    }

    enum SearchCondition {
      case SearchByMusicGenre(genres: List[MusicGenre])
      case SearchByLocation(locations: List[Location])
      case SearchByYearsActive(periodInYears: PeriodInYears)
      case SearchByHowLongActive(int: Int, until: Int)
    }

    def periodOverlapsWithPeriods(checkedPeriod: PeriodInYears, list: List[PeriodInYears]): Boolean = {
      list.exists(p => {
        checkedPeriod.start <= p.end && checkedPeriod.end >= p.start
      })
    }

    def wasArtistActive(artist: Artist, checkedPeriod: PeriodInYears): Boolean = {
      artist.yearsActive match {
        case YearsActive.StillActive(since, previousPeriods) => since <= checkedPeriod.end || periodOverlapsWithPeriods(checkedPeriod, previousPeriods)
        case YearsActive.ActiveInPeriods(periods) => periodOverlapsWithPeriods(checkedPeriod, periods)
      }
    }

    def activeLength(artist: Artist, currentYear: Int): Int = {
      val periods = artist.yearsActive match {
        case YearsActive.StillActive(since, previousPeriods) => previousPeriods.appended(PeriodInYears(since, currentYear))
        case YearsActive.ActiveInPeriods(periods) => periods
      }
      periods.map(currentPeriod => currentPeriod.end - currentPeriod.start).foldLeft(0)(((x, y) => x + y))
    }

    case class Artist(
                       name: String,
                       genre: MusicGenre,
                       origin: Location,
                       yearsActive: YearsActive
                     )

    def searchArtists(
                       artists: List[Artist],
                       searchConditions: List[SearchCondition]
                     ): List[Artist] = {
      artists.filter(artist =>
        searchConditions.forall(condition => {
          condition match {
            case SearchCondition.SearchByMusicGenre(genres) => genres.contains(artist.genre)
            case SearchCondition.SearchByLocation(locations) => locations.contains(artist.origin)
            case SearchCondition.SearchByYearsActive(periodInYears) => wasArtistActive(artist, periodInYears)
            case SearchCondition.SearchByHowLongActive(howLong, until) => activeLength(artist, until) >= howLong
          }
        })
      )

    }

  }

  {
    import artistsModelingDesignAndMaintainability.*
    import SearchCondition.*, MusicGenre.*, YearsActive.*

    val metallica = Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(1991, List(PeriodInYears(1981, 1984))))
    val ledZeppelin = Artist("Led Zeppelin", HardRock, Location("England"), ActiveInPeriods(List(PeriodInYears(1968, 1980))))
    val beeGees = Artist("Bee Gees", Pop, Location("England"), ActiveInPeriods(List(PeriodInYears(1958, 2003), PeriodInYears(2009, 2012))))
    val artists = List(metallica, ledZeppelin, beeGees)

    val popArtists = searchArtists(artists, List(SearchByMusicGenre(List(Pop))))
    println(popArtists)
    assert(popArtists == List(beeGees))

    val englishRock = searchArtists(
      artists,
      List(SearchByLocation(List(Location("England"))), SearchByMusicGenre(List(HardRock)))
    )
    println(englishRock)
    assert(englishRock == List(ledZeppelin))

    assert(searchArtists(artists, Nil) == artists)

    // SearchByYearsActive: overlap with any period, including gaps
    assert(searchArtists(artists, List(SearchByYearsActive(PeriodInYears(1985, 1990)))) == List(beeGees))
    assert(searchArtists(artists, List(SearchByYearsActive(PeriodInYears(1982, 1983)))) == List(metallica, beeGees))

    // SearchByHowLongActive: total years active up to `until` (37 / 12 / 48 for 2025)
    assert(searchArtists(artists, List(SearchByHowLongActive(40, 2025))) == List(beeGees))
    assert(searchArtists(artists, List(SearchByHowLongActive(10, 2025))) == artists)

    // combined conditions
    assert(
      searchArtists(artists, List(SearchByLocation(List(Location("England"))), SearchByHowLongActive(40, 2025))) ==
        List(beeGees)
    )

    // wasArtistActive: previous period, the gap between periods, and the current period
    assert(wasArtistActive(metallica, PeriodInYears(1982, 1983)))
    assert(!wasArtistActive(metallica, PeriodInYears(1985, 1990)))
    assert(wasArtistActive(metallica, PeriodInYears(2000, 2010)))
    assert(!wasArtistActive(beeGees, PeriodInYears(2004, 2008)))

    // activeLength: sums every period, StillActive counts up to currentYear
    assert(activeLength(metallica, 2025) == 37)
    assert(activeLength(beeGees, 2025) == 48)
  }
}
