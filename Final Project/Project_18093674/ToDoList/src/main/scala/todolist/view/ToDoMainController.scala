package todolist.view

import todolist.model.ToDo
import todolist.util.DateUtil._
import todolist.Main
import scalafx.scene.control.{TableView, TableColumn, Label}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty} 
import scalafx.Includes._
import scalafx.event._
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

@sfxml
class ToDoMainController (
    private val toDoListTable : TableView[ToDo],
    private val taskColumn : TableColumn[ToDo, String],
    private val taskLabel: Label,
    private val dateLabel: Label,
    private val subTask1Label: Label,
    private val subTask2Label: Label,
    private val subTask3Label: Label,
    private val subTask4Label: Label,
    private val subTask5Label: Label,
    private val subTask6Label: Label,
    private val subTask7Label: Label,
    private val completionLabel: Label
) {
    // initialize Table View display contents model
    toDoListTable.items = ToDo.toDoData
    //initialize table column
    taskColumn.cellValueFactory = {_.value.task}

    private def showTaskDetails (toDo : Option[ToDo]) = {
        toDo match {
            case Some(toDo) =>
            taskLabel.text <== toDo.task
            dateLabel.text = toDo.date().asString
            subTask1Label.text <== toDo.subTask1
            subTask2Label.text <== toDo.subTask2
            subTask3Label.text <== toDo.subTask3
            subTask4Label.text <== toDo.subTask4
            subTask5Label.text <== toDo.subTask5
            subTask6Label.text <== toDo.subTask6
            subTask7Label.text <== toDo.subTask7
            completionLabel.text <== toDo.completed

            case None =>
            // ToDo is null, remove all text
            taskLabel.text.unbind()
            dateLabel.text.unbind()
            subTask1Label.text.unbind()
            subTask2Label.text.unbind()
            subTask3Label.text.unbind()
            subTask4Label.text.unbind() 
            subTask5Label.text.unbind() 
            subTask6Label.text.unbind() 
            subTask7Label.text.unbind()
            completionLabel.text.unbind()

            taskLabel.text = "No Task"
            dateLabel.text = ""
            subTask1Label.text = ""
            subTask2Label.text = ""
            subTask3Label.text = ""
            subTask4Label.text = ""
            subTask5Label.text = ""
            subTask6Label.text = ""
            subTask7Label.text = ""
            completionLabel.text = ""
        }
    }
    showTaskDetails(None)

    toDoListTable.selectionModel().selectedItem.onChange(
        (_, _, newValue) => showTaskDetails(Option(newValue))
    )

    def deleteToDo(action: ActionEvent) = {
        val selectedToDo = toDoListTable.selectionModel().selectedIndex.value
        if (selectedToDo >= 0) {
            toDoListTable.items().remove(selectedToDo).delete();
        } else {
            // Nothing selected.
            val alert = new Alert(AlertType.Warning){
            initOwner(Main.stage)
            title       = "No Selection"
            headerText  = "No Task Selected"
            contentText = "Please select a Task in the table."
            }.showAndWait()
        }
    }

    def addToDo(action: ActionEvent) = {
        val toDo = new ToDo("")
        val okClicked = Main.showAddEdit(toDo)
        if(okClicked) {
            ToDo.toDoData += toDo
            toDo.save()
        }
    }

    def editToDo(action: ActionEvent) = {
        val selectedToDo = toDoListTable.selectionModel().selectedItem.value
        if (selectedToDo != null) {
            val okClicked = Main.showAddEdit(selectedToDo)
            if(okClicked) {
                showTaskDetails(Some(selectedToDo))
                selectedToDo.save()
            }           
        }
    }
}