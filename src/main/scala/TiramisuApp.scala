package org.tiramisu.app

import org.tiramisu._

case class Book(id:Int, name:String, author:String)

trait IndexController{self:Controller=>
  route -> {
    request.getRequestDispatcher("/store/books").forward(request, response)
  }
}

trait BookController{ self: Controller with BookRepository =>

  implicit def bookProvider = booksDao

  template("booksTemplate"){

    val routeBooks = route /"store"/"books"

    def composeBooks(selected:Book*) =
      compose("booksPage","books"->booksDao.all,"selected"->selected)

    routeBooks -> composeBooks()

    routeBooks /classOf[Book] -> {book=>
      composeBooks(book)
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
  with IndexController
  with BookController
