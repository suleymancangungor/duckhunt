import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.print.attribute.standard.Media;


public class Menu extends Application {

    public void start(Stage primaryStage) {
        //Background image
        Image backgroundImage = new Image(getClass().getResource("/assets/welcome/1.png").toString());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(800);
        backgroundImageView.setFitHeight(600);

        Font font = Font.font("Monospaced", FontWeight.BOLD, 24);
        Text menuText1 = new Text("PRESS ENTER TO PLAY");
        Text menuText2 = new Text("PRESS ESC TO EXIT");
        menuText1.setFont(font);
        menuText2.setFont(font);
        menuText1.setStyle("-fx-font-size: 24; -fx-fill: white;");
        menuText2.setStyle("-fx-font-size: 24; -fx-fill: white;");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuText1, menuText2);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setTranslateY(100);

        addBlinkingEffect(menuText1);
        addBlinkingEffect(menuText2);

        StackPane layout = new StackPane();
        layout.getChildren().addAll(backgroundImageView, vbox);

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Duck Hunt");
        primaryStage.show();

        //Background music
        MediaPlayer mediaPlayer = AudioManager.getInstance().playMusic("/assets/effects/Intro.mp3", true, true);

        //Wait until pressing key (enter/escape)
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                startGame(primaryStage);
            } else if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });
    }

    public void addBlinkingEffect(Text text) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> text.setVisible(true)),
                new KeyFrame(Duration.seconds(0.5), e -> text.setVisible(false)),
                new KeyFrame(Duration.seconds(1), e -> text.setVisible(true))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void startGame(Stage primaryStage) {

        OptionScreen optionScreen = new OptionScreen();
        optionScreen.start(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }


}
