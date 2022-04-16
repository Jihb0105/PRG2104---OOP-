package todolist.view
import todolist.model.ToDo
import todolist.Main
import todolist.util.DateUtil._
import scalafx.scene.control.{TextField, TableColumn, Label, Alert, DatePicker, CheckBox}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.beans.property.StringProperty

@sfxml
class AddEditController (
    private val taskField: TextField,
    private val datePicker: DatePicker,
    private val subTask1Field: TextField,
    private val subTask2Field: TextField,
    private val subTask3Field: TextField,
    private val subTask4Field: TextField,
    private val subTask5Field: TextField,
    private val subTask6Field: TextField,
    private val subTask7Field: TextField,
    private val completedCheckBox: CheckBox
){
    var dialogStage: Stage = null
    private var _toDo: ToDo = null
    var okClicked: Boolean = false

    def toDo = _toDo
    def toDo_=(x: ToDo) = {
        _toDo = x

        taskField.text = _toDo.task.value
        datePicker.value = _toDo.date.value
        subTask1Field.text = _toDo.subTask1.value
        subTask2Field.text = _toDo.subTask2.value
        subTask3Field.text = _toDo.subTask3.value
        subTask4Field.text = _toDo.subTask4.value
        subTask5Field.text = _toDo.subTask5.value
        subTask6Field.text = _toDo.subTask6.value
        subTask7Field.text = _toDo.subTask7.value
    }

    def ok(action: ActionEvent) = {
        
        _toDo.task <== taskField.text
        _toDo.date = datePicker.value
        _toDo.subTask1 <== subTask1Field.text
        _toDo.subTask2 <== subTask2Field.text
        _toDo.subTask3 <== subTask3Field.text    
        _toDo.subTask4 <== subTask4Field.text
        _toDo.subTask5 <== subTask5Field.text
        _toDo.subTask6 <== subTask6Field.text
        _toDo.subTask7 <== subTask7Field.text   

        okClicked = true
        dialogStage.close()
    }

    def cancel(action: ActionEvent) = {
        dialogStage.close()
    }

    def completion(action: ActionEvent) = {
        _toDo.completed = new StringProperty("Task Completed")
    }

}