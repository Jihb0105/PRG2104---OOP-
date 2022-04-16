package todolist.view
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import todolist.Main
import scalafx.event._
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

@sfxml
class RootController() {
    def closeApp() = {
        System.exit(0)
    }
    
    def showInstructionPage(action: ActionEvent) = {
        Main.showInstruction()
    }

    def showAppDetailPage(action: ActionEvent) = {
        Main.showAppDetail()
    }
}