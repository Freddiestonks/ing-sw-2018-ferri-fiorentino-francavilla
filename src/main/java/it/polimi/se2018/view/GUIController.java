package it.polimi.se2018.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIController extends Application {
    private static GUIView view = null;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private String action;
    @FXML
    private RadioButton socket;
    @FXML
    private RadioButton RMI;
    @FXML
    private TextField IP;
    @FXML
    private TextField username;
    @FXML
    private Button connect;
    @FXML
    private ToggleGroup connectiontype;
    @FXML
    private Label connectionState;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label player4;

    @FXML
    private void handleConnectButtonAction(ActionEvent event){
        if(connectiontype.getSelectedToggle().equals(socket)){
            getGuiView().setConnected(true);
            action = "connect,socket," + IP.getText();
            view.setUserInput(action);
            action = "set,username," + username.getText();
            view.setUserInput(action);
            updateConnectionState(getGuiView().isConnected());

        }
        else if(connectiontype.getSelectedToggle().equals(RMI)){
            getGuiView().setConnected(true);
            action = "connect,rmi," + IP.getText();
            view.setUserInput(action);
            action = "set,username," + username.getText();
            view.setUserInput(action);
            updateConnectionState(getGuiView().isConnected());
            action = "";
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initRootLayout();

    }

    public void updateConnectionState(boolean connected){
        if(connected){
            Platform.runLater(()->{
                    connectionState.setText("CONNECTED");
            connectionState.setStyle("-fx-text-fill: chartreuse");});

        }
        else {
            Platform.runLater(()->{
                connectionState.setText("NOT CONNECTED");
                connectionState.setStyle("-fx-text-fill: red");});
        }

    }

    public GUIView getGuiView() {
        return view;
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIController.class.getResource("/fxml/MainScreen.fxml"));
            rootLayout = loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sagrada - Connection Handler");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectClick(){}

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setView(GUIView view) {
        this.view = view;
    }

    public String getAction() {
        return action;
    }
}