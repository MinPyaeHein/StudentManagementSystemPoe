package Controller;

import Dto.LoginDto;
import Model.Student;
import Service.impl.LoginService;
import Service.impl.StudentServiceImpl;
import Utils.AlertUtil;
import Utils.UtilConstants;
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
    private TextField gmailField;

    @FXML
    private PasswordField passwordField;

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
    }

    @FXML
    private void submit() {
        String gmail = gmailField.getText().trim();
        String password = passwordField.getText().trim();
        LoginDto login = new LoginDto(gmail,password);
        try{
            loginService.login(login);
            displayView();
        } catch (RuntimeException exception) {
            AlertUtil.alert(exception.getMessage(), UtilConstants.ERROR_ALERT);
            cancel();
        }
    }


    @FXML
    private void cancel() {
        gmailField.clear();
        passwordField.clear();
    }

    public void displayView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/mylearningproject/main-view.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.setHeight(700);
            stage.setWidth(1500);
            stage.centerOnScreen();

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
