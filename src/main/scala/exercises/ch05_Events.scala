package exercises

object ch05_Events extends App {
  case class Event(name: String, start: Int, end: Int)

  def validateName(name: String): Option[String] = if (name.size > 0) Some(name) else None

  def validateEnd(end: Int): Option[Int] = if (end < 3000) Some(end) else None

  def validateStart(start: Int, end: Int): Option[Int] = if (start <= end) Some(start) else None

  def validateLength(start: Int, end: Int, minLength: Int): Option[Int] = {
    val length = end - start
    if (length >= minLength) Some(length) else None
  }

  def parse(name: String, start: Int, end: Int, minLength: Int): Option[Event] = {

    for {
      validatedName   <- validateName(name)
      validatedStart  <- validateStart(start, end)
      validatedEnd    <- validateEnd(end)
      validatedLength <- validateLength(start, end, minLength)
    } yield Event(validatedName, validatedStart, validatedEnd)
  }

  def parse2(name: String, start: Int, end: Int, minLength: Int): Option[Event] = {
    validateName(name).flatMap(vn =>
      validateStart(start, end).flatMap(vs =>
        validateEnd(end).flatMap(ve =>
          validateLength(start, end, minLength).map(vl =>
            Event(vn, vs, ve)))
      )
    )
  }

  {
    println(parse("Apollo Program", 1961, 1972, 10))
    println(parse("Apollo Program", 1961, 1972, 100))
    println(parse2("Apollo Program", 1961, 1972, 10))
    println(parse2("Apollo Program", 1961, 1972, 100))
  }
}
