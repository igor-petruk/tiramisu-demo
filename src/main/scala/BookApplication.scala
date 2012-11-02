package org.tiramisu.app

import org.tiramisu._
import org.tiramisu.providers._
import annotation.ClassfileAnnotation

case class Book(id:Int, name:String, author:String)

trait BookController extends Extra { self: Controller with BookRepository =>
  implicit def bookProvider = booksDao

  def session[T] = Bean[T](sessionScope) _

  @Exposed
  val sessionBook = session[Book](null)

  route ->  response.sendRedirect("/store/books/1")

  template("booksTemplate"){
    route /"store"/"books"/classOf[Book] -> {book=>
      sessionBook.value = book
      compose("booksPage", "books"->booksDao.all,"selected"->List(book))
    }
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

    def provide(id:String) = booksIndex.getOrElse(Integer.parseInt(id), null)
  }
}

class BookApplication extends Tiramisu
  with BookRepository
  with BookController
