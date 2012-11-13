package org.tiramisu.app

import org.tiramisu._
import org.tiramisu.providers._

case class Book(id:Int, name:String, author:String)

trait BookController { self: Controller with BookRepository =>
  implicit def bookProvider = booksDao

  val visited = sessionBean[String]("")

  val books = requestBean[List[Book]]()

  val selected = requestBean[Option[Book]]()

  route ->  response.sendRedirect("/store/books/1")

  route /"store"/"books"/opt(classOf[Book]) -> {book=>
    visited.value += 1
    books.value = booksDao.all
    selected.value = book
   // sessionBook.value = book
    compose("booksPage", "books"->books,"selected"->selected, "visited"->visited)
  }
}

trait BookRepository{
  lazy val booksDao = new EntityProvider[Book]{
    val all = List(
      Book(1, "War and Peace", "Tolstoy"),
      Book(2, "Dogs Heart","Bulgakov"),
      Book(3, "Сказки", "Пушкин")
    )
    
    val booksIndex = all.groupBy(_.id).mapValues(_.head)

    def provide(id:String) = booksIndex.get(Integer.parseInt(id)).getOrElse(null)
  }
}

trait SomethingOther {
  val cookie:Int = 10
}

class BookApplication extends Tiramisu
  with BookRepository
  with BookController
 with SomethingOther
