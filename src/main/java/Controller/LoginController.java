package Controller;

import Model.Login;
import Model.Student;
import Service.impl.LoginService;
import Service.impl.StudentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField studentNameField;

    @FXML
    private TextField studentIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    private StudentServiceImpl studentService;
    private LoginService loginService;


    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        studentService=new StudentServiceImpl();
        loginService=new LoginService();
        studentIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                studentIdField.setText(oldValue);
            }
        });
    }

    @FXML
    private void submit() {
        String studentName = studentNameField.getText();
        String studentId = studentIdField.getText();
        String password = passwordField.getText();
        Login login = new  Login(studentName,studentId,password);
        Student student= loginService.submit(login);
        if(student!=null){
            displayView();
        }
        cancel();
    }


    @FXML
    private void cancel() {
        studentNameField.clear();
        studentIdField.clear();
        passwordField.clear();
    }

    public void displayView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/mylearningproject/main-view.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.setFullScreen(false);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
