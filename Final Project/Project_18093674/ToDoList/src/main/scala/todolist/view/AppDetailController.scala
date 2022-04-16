package todolist.view
import todolist.Main
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert

@sfxml
class AppDetailController {
    var dialogStage: Stage = null
    var returnClicked: Boolean = false

    def returnClicked(action: ActionEvent) = {
        dialogStage.close()
    }
}