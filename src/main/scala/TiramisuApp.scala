package org.tiramisu.app

import org.tiramisu._
import org.tiramisu.providers._
import java.util.Date

case class Book(name:String)

trait IndexController{self:Controller=>
  route /"index" -> view("index")
}

trait BookController{ self: Controller with BookRepository =>

  implicit def bookProvider = booksDao

  val routeBooks = route /"store"/string/"books" /classOf[Book]

  template("template1"){

    routeBooks/"view" -> { (storeName, book)=>
      view("index", book, "store"->storeName, new Date)
    }

    case class Item(fName:String, fLastName:String){
      val name = fName
      val lastName = fLastName
    }

    routeBooks /"compose" ->{ (storeName, book)=>
      compose("page1",
              book,
              "store"->storeName,
              new Date,
              "items"->List(
                Item("Igor","Petruk"),
                Item("Rocksy","Seletska")
              ))
    }

    routeBooks /"compose2" ->{ (storeName, book)=>
      compose("page2",
        book,
        "store"->storeName,
        new Date,
        "items"->List())
    }

    route /"history"/string -> { (page)=>
      request.getRequestDispatcher("/history.html").forward(request,response)
    }
  }
}

trait BookRepository{
  lazy val booksDao = new EntityProvider[Book]{
    def provide(id:String) = Book("Book"+id)
  }
}

class BookApplication extends Tiramisu
  with BookRepository
  with IndexController
  with BookController
