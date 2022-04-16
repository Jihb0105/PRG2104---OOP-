package todolist.util
import scalikejdbc._
import todolist.model.ToDo

trait Database {
    val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

    val dbQuery = "jdbc:derby:toDoDB;create=true;"
    //initialize JDBC driver & connection pool
    Class.forName(derbyDriverClassname)

    ConnectionPool.singleton(dbQuery, "me", "mine")

    //ad-hoc session provider on the REPL
    implicit val session = AutoSession
}

object Database extends Database {
    def setupDB() = {
  	    if (!hasDBInitialize)
  		ToDo.createTable()
    }
    def hasDBInitialize : Boolean = {
        DB getTable "ToDo" match {
            case Some(x) => true
            case None => false
        }
    }
}