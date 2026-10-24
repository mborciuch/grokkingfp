lazy val root = (project in file("."))
  .settings(
    name            := "grokkingfp-examples",
    organization    := "Michał Płachta (Manning)",
    version         := "1.0",
    scalaVersion    := "3.6.4",
    scalacOptions ++= List("-unchecked", "-deprecation", "-explain"),
    libraryDependencies ++= Seq(
      "org.typelevel"     %% "cats-effect"      % "3.6.0",
      "co.fs2"            %% "fs2-core"         % "3.11.0",
      "org.scalatest"     %% "scalatest"        % "3.2.19"   % Test,
      "org.scalatestplus" %% "scalacheck-1-16"  % "3.2.14.0" % Test,
      // imperative libraries:
      "com.typesafe.akka"  % "akka-actor_2.13"  % "2.6.20",
      "org.apache.jena"    % "apache-jena-libs" % "5.3.0",
      "org.apache.jena"    % "jena-fuseki-main" % "5.3.0",
      "org.slf4j"          % "slf4j-nop"        % "2.0.17"
    ),
    initialCommands := s"""
      import fs2._, cats.effect._, cats.implicits._, cats.effect.unsafe.implicits.global
      import scala.concurrent.duration._, java.util.concurrent._
      import scala.jdk.javaapi.CollectionConverters.asScala
      import org.apache.jena.query._, org.apache.jena.rdfconnection._
    """,
    run / fork      := true,
    run / javaOptions += "-ea",
    addCommandAlias(
      "runAll",
      ";runMain lesson.ch01_Intro" +
        ";runMain lesson.ch01_IntroScala" +
        ";runMain lesson.ch02_ShoppingCartDiscounts" +
        ";runMain lesson.ch02_TipCalculation" +
        ";runMain lesson.ch02_PureFunctions" +
        ";runMain lesson.ch02_ShoppingCartDiscountsScala" +
        ";runMain lesson.ch02_TipCalculationScala" +
        ";runMain lesson.ch02_TestingPureFunctions" +
        ";runMain lesson.ch03_ItineraryCopying" +
        ";runMain lesson.ch03_ItineraryOO" +
        ";runMain lesson.ch03_LapTimes" +
        ";runMain lesson.ch03_ListVsString" +
        ";runMain lesson.ch03_ListVsStringScala" +
        ";runMain lesson.ch03_AbbreviateNames" +
        ";runMain lesson.ch03_ItineraryScala" +
        ";runMain lesson.ch03_SlicingAndAppending" +
        ";runMain lesson.ch04_WordScoring" +
        ";runMain lesson.ch04_JavaFunctionIntro" +
        ";runMain lesson.ch04_WordScoringScala" +
        ";runMain lesson.ch04_PassingFunctions" +
        ";runMain lesson.ch04_ReturningFunctions" +
        ";runMain lesson.ch04_ProgrammingLanguages" +
        ";runMain lesson.ch05_BookAdaptations" +
        ";runMain lesson.ch05_BookFriendRecommendationsJava" +
        ";runMain lesson.ch05_BookFriendRecommendations" +
        ";runMain lesson.ch05_SequencedNestedFlatMaps" +
        ";runMain lesson.ch05_Points2d3d" +
        ";runMain lesson.ch05_RandomForComprehensions" +
        ";runMain lesson.ch05_PointsInsideCircles" +
        ";runMain lesson.ch05_StatementsVsExpressionsJava" +
        ";runMain lesson.ch05_StatementsVsExpressions" +
        ";runMain lesson.ch05_Events" +
        ";runMain lesson.ch06_TvShows" +
        ";runMain lesson.ch06_TvShowsJava" +
        ";runMain lesson.ch07_MusicArtistsSearch" +
        ";runMain lesson.ch07_Playlist" +
        ";runMain lesson.ch08_SchedulingMeetingsImpure" +
        ";runMain lesson.ch08_SchedulingMeetings" +
        ";runMain lesson.ch08_CardGame" +
        ";runMain lesson.ch08_CastingDie" +
        ";runMain lesson.ch09_CurrencyExchange" +
        ";runMain lesson.ch09_Stream123s" +
        ";runMain lesson.ch09_CastingDieStream" +
        ";runMain lesson.ch10_CheckIns" +
        ";runMain lesson.ch10_CheckInsImperative" +
        ";runMain lesson.ch10_CastingDieConcurrently" +
        ";runMain lesson.ch11_TravelGuide" +
        ";runMain lesson.ch12_TravelGuide" +
        ";runMain lesson.chA_ScalaCheatSheet"
    )
  )
