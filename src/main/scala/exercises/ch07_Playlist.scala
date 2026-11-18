package exercises

import exercises.ch07_MusicArtistsSearchv2.artistsProductType.MusicGenre

object ch07_Playlist {

  enum Genre {
    case Pop
    case Metal
    case Rock
  }

  case class Artist(name: String)
  case class User(name: String)

  enum PlaylistType {
    case ByUser(user: User)
    case ByArtist(artist: Artist)
    case ByGenres(genres: Set[MusicGenre])
  }

  case class Song(artist: Artist, name: String )

  case class Playlist(name: String, kind: Genre, songs:List[Song])
}
