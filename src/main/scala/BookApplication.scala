package org.tiramisu.app

import org.tiramisu._
import org.tiramisu.providers._

case class Book(id:Int, name:String, author:String)

trait BookController{ self: Controller with BookRepository =>
  implicit def bookProvider = booksDao

  template("booksTemplate"){

    route /"store"/"books"/classOf[Book] -> {book=>
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
    
    val booksIndex = all.groupBy(_.id).map{item=>(item._1,item._2.head)}

    def provide(id:String) = booksIndex.getOrElse(Integer.parseInt(id), null)
  }
}

class BookApplication extends Tiramisu
  with BookRepository
  with BookController
