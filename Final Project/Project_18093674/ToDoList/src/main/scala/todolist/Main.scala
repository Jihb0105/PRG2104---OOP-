package todolist
import todolist.view.AddEditController
import todolist.view.InstructionController
import todolist.view.AppDetailController
import todolist.model.ToDo
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.animation.FadeTransition 
import scalafx.util.Duration
import scalafx.stage.{Stage, Modality}
import scalafx.scene.image.Image

object Main extends JFXApp{
    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    // initialize the loader object.
    val loader = new FXMLLoader(rootResource, NoDependencyResolver)
    // Load root layout from fxml file.
    loader.load();
    // retrieve the root component BorderPane from the FXML 
    val roots = loader.getRoot[jfxs.layout.BorderPane]
    // initialize stage
    stage = new PrimaryStage {
        icons += new Image(getClass.getResourceAsStream("/images/logo.png"))
        title = "ToDo List"
        scene = new Scene {
        root = roots
        }
    }

    // actions for display todomainpage window 
    def showToDoList() = {
        val resource = getClass.getResource("view/ToDoMainPage.fxml")
        val loader = new FXMLLoader(resource, NoDependencyResolver)
        loader.load();
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        this.roots.setCenter(roots)
    } 

    def showWelcome() = {
        val resource = getClass.getResource("view/SplashScreen.fxml")
        val loader = new FXMLLoader(resource, NoDependencyResolver)
        loader.load();
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        this.roots.setCenter(roots)
        this.roots.setStyle("-fx-background-color: #c5e6ef")

        /*
        Title: JavaFX Splash Screen / Welcome Screen (Inside Same Window)
        Author: Genuine Coder
        Date: 2016
        Type: Youtube
        Availability: https://www.youtube.com/watch?v=muz6QLIgrC0&ab_channel=GenuineCoder
        */

        val fadeIn = new FadeTransition(new Duration(2000), roots){
            fromValue = 0
            toValue = 1
            cycleCount = 1
        }
        val fadeOut = new FadeTransition(new Duration(2000), roots){
            fromValue = 1
            toValue = 0
            cycleCount = 1
        }
        fadeIn.play()
        fadeIn.onFinished = {_ =>
            fadeOut.play()
        }
        fadeOut.onFinished = {_ =>
            showToDoList()
        }
    }
    // call to display Welcome page when app start
    showWelcome()

    def showAddEdit(toDo: ToDo): Boolean = {
        val resource = getClass.getResourceAsStream("view/AddEditDialog.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource);
        val roots2 = loader.getRoot[jfxs.Parent]
        val controller = loader.getController[AddEditController#Controller]
        
        val dialog = new Stage() {
           initModality(Modality.APPLICATION_MODAL)
           initOwner(stage)
           scene = new Scene {
               icons += new Image(getClass.getResourceAsStream("/images/logo.png"))
               root = roots2
           } 
        }
        controller.dialogStage = dialog
        controller.toDo = toDo
        dialog.showAndWait()
        controller.okClicked
    }

    def showInstruction() = {
        val resource = getClass.getResourceAsStream("view/Instructions.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource);
        val roots2 = loader.getRoot[jfxs.Parent]
        val controller = loader.getController[InstructionController#Controller]

        val dialog = new Stage() {
            initModality(Modality.APPLICATION_MODAL)
            initOwner(stage)
            scene = new Scene {
                icons += new Image(getClass.getResourceAsStream("/images/logo.png"))
                root = roots2
            } 
        }
        controller.dialogStage = dialog
        dialog.showAndWait()
        controller.acceptClicked
    }

    def showAppDetail() = {
        val resource = getClass.getResourceAsStream("view/AppDetail.fxml")
        val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource);
        val roots2 = loader.getRoot[jfxs.Parent]
        val controller = loader.getController[AppDetailController#Controller]

        val dialog = new Stage() {
            initModality(Modality.APPLICATION_MODAL)
            initOwner(stage)
            scene = new Scene {
                icons += new Image(getClass.getResourceAsStream("/images/logo.png"))
                root = roots2
            } 
        }
        controller.dialogStage = dialog
        dialog.showAndWait()
        controller.returnClicked
    }
}