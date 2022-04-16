package todolist.view
import todolist.Main
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert

@sfxml
class InstructionController {
    var dialogStage: Stage = null
    var acceptClicked: Boolean = false

    def acceptClicked(action: ActionEvent) = {
        dialogStage.close()
    }
}