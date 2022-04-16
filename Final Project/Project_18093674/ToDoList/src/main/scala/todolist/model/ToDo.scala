package todolist.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import java.time.LocalDate;
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.DatePicker
import todolist.util.Database
import todolist.util.DateUtil._
import scalikejdbc._
import scala.util.{ Try, Success, Failure }

class ToDo (t: String) extends Database{
    def this() = this(null)
    var task = new StringProperty(t)
    var date = ObjectProperty[LocalDate](LocalDate.now())
    var subTask1 = new StringProperty("")
    var subTask2 = new StringProperty("")
    var subTask3 = new StringProperty("")
    var subTask4 = new StringProperty("")
    var subTask5 = new StringProperty("")
    var subTask6 = new StringProperty("")
    var subTask7 = new StringProperty("")
    var completed = new StringProperty("")

    def save(): Try[Int] = {
        if (!(isExist)) {
            Try(DB autoCommit{implicit session =>
                sql"""
                    INSERT INTO ToDo (task, date, subTask1, subTask2, subTask3, subTask4, subTask5, subTask6, subTask7, completed) VALUES
                        (${task.value}, ${date.value.asString}, ${subTask1.value}, ${subTask2.value}, ${subTask3.value}, ${subTask4.value}, ${subTask5.value}, ${subTask6.value}, ${subTask7.value}, ${completed.value})
                """.update.apply() 
            })
        }else{
            Try(DB autoCommit{implicit session =>
                sql"""
                    UPDATE ToDo SET 
                    task = ${task.value},
                    date = ${date.value.asString},
                    subTask1 = ${subTask1.value},
                    subTask2 = ${subTask2.value},
                    subTask3 = ${subTask3.value},
                    subTask4 = ${subTask4.value},
                    subTask5 = ${subTask5.value},
                    subTask6 = ${subTask6.value},
                    subTask7 = ${subTask7.value},
                    completed = ${completed.value}
                    WHERE task = ${task.value}
                """.update.apply()
            })
        }
    }

    def delete(): Try[Int] = {
        if (isExist) {
            Try(DB autoCommit {implicit session =>
                sql"""
                    DELETE FROM ToDO WHERE
                        task = ${task.value}
                """.update.apply()
            })
        }else
            throw new Exception("Task not available in database")
    }

    def isExist(): Boolean = {
        DB readOnly{implicit session =>
            sql"""
            SELECT * FROM ToDo WHERE
            task = ${task.value}
            """.map(rs => rs.string("task")).single.apply()
        }match{
            case Some(x) => true
            case None => false
        }
    }
}

object ToDo extends Database {
    //the data as an observable list of ToDo
    val toDoData = new ObservableBuffer[ToDo]()
    Database.setupDB
    toDoData ++= ToDo.getAllToDo

    def apply (
        taskD: String,
        dateD: String,
        subTask1D: String,
        subTask2D: String,
        subTask3D: String,
        subTask4D: String,
        subTask5D: String,
        subTask6D: String,
        subTask7D: String,
        completedD: String

    ): ToDo = {
        new ToDo(taskD){
            date.value = dateD.parseLocalDate
            subTask1.value = subTask1D
            subTask2.value = subTask2D
            subTask3.value = subTask3D
            subTask4.value = subTask4D
            subTask5.value = subTask5D
            subTask6.value = subTask6D
            subTask7.value = subTask7D
            completed.value = completedD
        }
    }

    def createTable() = {
        DB autoCommit { implicit session =>
            sql"""
            create table ToDo (
                id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                task VARCHAR(100),
                date VARCHAR(64),
                subTask1 VARCHAR(100),
                subTask2 VARCHAR(100),
                subTask3 VARCHAR(100),
                subTask4 VARCHAR(100),
                subTask5 VARCHAR(100),
                subTask6 VARCHAR(100),
                subTask7 VARCHAR(100),
                completed VARCHAR(50)
            )
            """.execute.apply()
        }
    }

    def getAllToDo: List[ToDo] = {
        DB readOnly {implicit session =>
            sql"SELECT * FROM ToDo".map(rs => ToDo(rs.string("task"), rs.string("date"), rs.string("subTask1"), rs.string("subTask2"), rs.string("subTask3"), rs.string("subTask4"), rs.string("subTask5"), rs.string("subTask6"), rs.string("subTask7"), rs.string("completed") )).list.apply()
        }
    }
}