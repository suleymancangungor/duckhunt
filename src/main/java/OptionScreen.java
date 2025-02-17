import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OptionScreen extends Application {

    public Canvas canvas;
    public GraphicsContext gc;
    public boolean running = false;
    public static Image[] backgroundImages;
    public static Image[] crosshairImages;
    public static Image[] foregroundImages;
    public static int currentBackground = 1;
    public static int currentCrosshair = 1;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();

        backgroundImages = new Image[]{new Image("/assets/background/1.png"), new Image("/assets/background/2.png"), new Image("/assets/background/3.png"),
                new Image("/assets/background/4.png"), new Image("/assets/background/5.png"), new Image("/assets/background/6.png")};
        foregroundImages = new Image[]{new Image("/assets/foreground/1.png"), new Image("/assets/foreground/2.png"), new Image("/assets/foreground/3.png"),
                new Image("/assets/foreground/4.png"), new Image("/assets/foreground/5.png"), new Image("/assets/foreground/6.png")};
        crosshairImages = new Image[]{new Image("/assets/crosshair/1.png"), new Image("/assets/crosshair/2.png"), new Image("/assets/crosshair/3.png"),
                new Image("/assets/crosshair/4.png"), new Image("/assets/crosshair/5.png"), new Image("/assets/crosshair/6.png")};

        gc.drawImage(backgroundImages[0], 0, 0, 800, 600);
        gc.drawImage(crosshairImages[0],380, 280, 40, 40);

        Font font = Font.font("Monospaced", FontWeight.BOLD, 24);
        Text optionScreenText1 = new Text("USE ARROW KEYS TO NAVIGATE");
        Text optionScreenText2 = new Text("PRESS ENTER TO START");
        optionScreenText1.setFont(font);
        optionScreenText2.setFont(font);
        optionScreenText1.setStyle("-fx-font-size: 24; -fx-fill: white;");
        optionScreenText2.setStyle("-fx-font-size: 24; -fx-fill: white;");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(optionScreenText1,optionScreenText2);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setTranslateY(-250);

        StackPane layout = new StackPane();
        layout.getChildren().addAll(canvas, vbox);

        Scene scene = new Scene(layout);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Duck Hunt");
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) { //Changes crosshair image.
                if (currentCrosshair != 6) {
                    currentCrosshair++;
                } else {
                    currentCrosshair = 1;
                }
                changeCrosshair();

            } else if (event.getCode() == KeyCode.DOWN) { //Changes crosshair image.
                if (currentCrosshair != 1) {
                    currentCrosshair--;
                } else {
                    currentCrosshair = 6;
                }
                changeCrosshair();

            } else if (event.getCode() == KeyCode.RIGHT) { //Changes background image.
                if (currentBackground != 6) {
                    currentBackground++;
                } else {
                    currentBackground = 1;
                }
                changeBackground();

            } else if (event.getCode() == KeyCode.LEFT) { //Changes background image.
                if (currentBackground != 1) {
                    currentBackground--;
                } else {
                    currentBackground = 6;
                }
                changeBackground();

            } else if (event.getCode() == KeyCode.ENTER) { //Starts game.

                AudioManager.getInstance().stopMusic();
                DuckHunt duckHunt = new DuckHunt();
                duckHunt.start(primaryStage);

            } else if (event.getCode() == KeyCode.ESCAPE) { //Go back to menu screen.

                primaryStage.setScene(new Scene(new StackPane())); //Clear the current scene
                new Menu().start(primaryStage);
            }
        });

    }

    public void changeCrosshair() {
        gc.clearRect(0, 0, 800, 600);
        gc.drawImage(backgroundImages[currentBackground - 1], 0, 0, 800, 600);
        gc.drawImage(crosshairImages[currentCrosshair - 1], 380, 280, 40, 40);
    }

    public void changeBackground() {
        gc.clearRect(0, 0, 800, 600);
        gc.drawImage(backgroundImages[currentBackground - 1], 0, 0, 800, 600);
        gc.drawImage(crosshairImages[currentCrosshair - 1], 380, 280, 40, 40);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
