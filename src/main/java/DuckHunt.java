import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;

public class DuckHunt extends Application{

    public ArrayList<Duck> ducks;
    public Image backgroundImage;
    public Image foregroundImage;
    public Image crosshairImage;
    public ImageView backgroundView;
    public ImageView foregroundView;
    public ImageView crosshairView;
    public double crosshairX;
    public double crosshairY;
    public boolean pause = false;
    public int maxLevel = 6;
    public int level = 1;

    @Override
    public void start(Stage primaryStage){

        Gun gun = new Gun();
        ducks = new ArrayList<>();
        backgroundImage = OptionScreen.backgroundImages[OptionScreen.currentBackground - 1];
        foregroundImage = OptionScreen.foregroundImages[OptionScreen.currentBackground - 1];
        crosshairImage = OptionScreen.crosshairImages[OptionScreen.currentCrosshair - 1];

        backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600);

        foregroundView = new ImageView(foregroundImage);
        foregroundView.setFitWidth(800);
        foregroundView.setFitHeight(600);

        crosshairView = new ImageView(crosshairImage);
        crosshairView.setFitWidth(11);
        crosshairView.setFitHeight(11);
        crosshairView.setMouseTransparent(true);

        Font font = Font.font("Monospaced", FontWeight.BOLD, 24);
        Text gameText1 = new Text("Level: "+ level +"/6");
        Text gameText2 = new Text("Ammo Left: "+ gun.getBulletCount());
        gameText1.setFont(font);
        gameText2.setFont(font);
        gameText1.setStyle("-fx-font-size: 24; -fx-fill: white;");
        gameText2.setStyle("-fx-font-size: 24; -fx-fill: white;");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(gameText1, gameText2);

        Pane layout = new Pane();
        layout.getChildren().addAll(backgroundView, vbox);

        Scene scene = new Scene(layout);
        scene.setCursor(javafx.scene.Cursor.NONE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Duck Hunt");
        primaryStage.show();

        scene.setOnMouseMoved(event -> {
            crosshairX = event.getX() - crosshairView.getFitWidth() / 2;
            crosshairY = event.getY() - crosshairView.getFitHeight() / 2;
            crosshairView.setLayoutX(crosshairX);
            crosshairView.setLayoutY(crosshairY);
        });
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (gun.getBulletCount() > 0) {
                AudioManager.getInstance().playMusic("assets/effects/Gunshot.mp3", false, false);
                for(Duck duck : ducks){
                    if (duck.isHit(crosshairX, crosshairY)) {
                        duck.setStatus();
                    }
                }
            }
            gun.Shoot();
            gameText2.setText("Ammo Left: "+gun.getBulletCount());
        });
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                gun.Reload();
            }
            gameText2.setText("Ammo Left: "+gun.getBulletCount());
        });

        spawnDuck(layout); //for the first level
        layout.getChildren().addAll(foregroundView,crosshairView);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //System.out.println(ducks.size());
                checkDucksCollide();

                didUserLost(gun); // lose screen

                for (int i = ducks.size() - 1; i >= 0; i--) {
                    ducks.get(i).update();

                    if (ducks.get(i).y > 600 && !ducks.get(i).getStatus()) {
                        ducks.remove(i);
                        if (ducks.isEmpty()) {
                            pause = true;
                            layout.getChildren().removeAll(foregroundView,crosshairView);
                        }
                    }
                }

                if (ducks.isEmpty() && pause){
                    level++;
                    if (level > maxLevel) {
                        stop(); // finish game, win screen!
                        return;
                    }
                    pause = false;
                    EventHandler<MouseEvent> mouseBlocker = Event::consume;
                    scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseBlocker);
                    MediaPlayer mediaPlayer = AudioManager.getInstance().playMusic("/assets/effects/LevelCompleted.mp3", false, true);
                    mediaPlayer.setOnEndOfMedia(()->{
                        for (int i = 0; i < level; i++){
                            spawnDuck(layout);
                        }
                        layout.getChildren().addAll(foregroundView,crosshairView);
                        gun.setCapacity(ducks.size()*3);
                        gun.setBulletCount(ducks.size()*3);
                        gameText1.setText("Level: "+level+"/6");
                        gameText2.setText("Ammo Left: "+gun.getBulletCount());
                        scene.removeEventFilter(MouseEvent.MOUSE_CLICKED, mouseBlocker);
                    });
                }
            }
        }.start();
    }

    public static void main(String[] args){
        launch(args);
    }

    public void spawnDuck(Pane layout){
        Duck duck = new Duck();
        ducks.add(duck);
        if (!layout.getChildren().contains(duck.getDuckView())){
            layout.getChildren().addAll(duck.getDuckView());
        }
    }

    public void didUserLost(Gun gun){
        if (gun.getBulletCount() < ducks.size()) {
            //Get lose screen!
        }
    }

    // Ducks are blocking each other so they cannot move!!!!!!!!
    public void checkDucksCollide(){
        for (int i = 0; i< ducks.size()-1; i++){
            for (int j = i+1; j< ducks.size(); j++) {
                if (ducks.get(i).isColliding(ducks.get(j))){
                    ducks.get(i).changeDirection(ducks.get(j));
                }
            }
        }
    }

}
