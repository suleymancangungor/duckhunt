import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

public class Duck {
    int counter = 0;
    int imageIndex = 0;
    int negativeYSpeedImageIndex = 0;
    private ImageView duckView;
    private Image[] duckImages;
    double x = 400;
    double y = 430;
    double speedX = 2;
    double speedY = -2;
    private boolean isAlive = true;

    public Duck(){
        Random random = new Random();
        int randomIndex = random.nextInt(3);
        chooseColor(randomIndex);

        this.duckView = new ImageView(duckImages[0]);

        this.x = new Random().nextDouble(800);
        this.speedX = new Random().nextDouble() * 1.5 + 0.5;
        this.speedY = new Random().nextDouble() * -1.5 - 0.5;

        duckView.setLayoutX(x);
        duckView.setLayoutY(y);
    }

    public void update() {
        if (!isAlive) {
            duckView.setImage(duckImages[7]);
            y += 2;
        } else {
            x += speedX;
            y += speedY;

            if (x <= 0) {
                duckView.setScaleX(1);
                speedX *= -1;
            } else if (x >= 800) {
                duckView.setScaleX(-1);
                speedX *= -1;
            }
            if (y >= 430) {
                duckView.setScaleY(1);
                speedY *= -1;
            } else if (y <= 0) {
                speedY *= -1;
            }
            counter++;
            if (counter == 10){
                imageIndex = (imageIndex + 1) % 3;
                negativeYSpeedImageIndex = (negativeYSpeedImageIndex + 1) % 3 + 3;
                if (speedY > 0) {
                    duckView.setImage(duckImages[negativeYSpeedImageIndex]);
                } else {
                    duckView.setImage(duckImages[imageIndex]);
                }
                counter = 0;
            }
        }

        duckView.setLayoutX(x);
        duckView.setLayoutY(y);
    }


    public void chooseColor(int index){
        switch (index){
            case 0:
                duckImages = new Image[]{new Image("assets/duck_black/1.png"), new Image("assets/duck_black/2.png"), new Image("assets/duck_black/3.png"),
                        new Image("assets/duck_black/4.png"), new Image("assets/duck_black/5.png"), new Image("assets/duck_black/6.png"),
                        new Image("assets/duck_black/7.png"), new Image("assets/duck_black/8.png")};
                break;
            case 1:
                duckImages = new Image[]{new Image("assets/duck_blue/1.png"), new Image("assets/duck_blue/2.png"), new Image("assets/duck_blue/3.png"),
                        new Image("assets/duck_blue/4.png"), new Image("assets/duck_blue/5.png"), new Image("assets/duck_blue/6.png"),
                        new Image("assets/duck_blue/7.png"), new Image("assets/duck_blue/8.png")};
                break;
            case 2:
                duckImages = new Image[]{new Image("assets/duck_red/1.png"), new Image("assets/duck_red/2.png"), new Image("assets/duck_red/3.png"),
                        new Image("assets/duck_red/4.png"), new Image("assets/duck_red/5.png"), new Image("assets/duck_red/6.png"),
                        new Image("assets/duck_red/7.png"), new Image("assets/duck_red/8.png")};
                break;
        }
    }

    public boolean isHit(double mouseX, double mouseY) {
        double width = duckView.getImage().getWidth();
        double height = duckView.getImage().getHeight();

        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            duckView.setImage(duckImages[6]);
            AudioManager.getInstance().playMusic("assets/effects/Gunshot.mp3", false, false);
            AudioManager.getInstance().playMusic("assets/effects/DuckFalls.mp3", false, false);
            return true;
        }
        return false;
    }

    public void setStatus(){
        isAlive = false;
    }

    public boolean getStatus(){
        return isAlive;
    }

    public ImageView getDuckView(){
        return duckView;
    }

    public void setImageAfterCollision(){
        if (speedX < 0 && speedY < 0) {
            imageIndex = 0;
            duckView.setImage(duckImages[imageIndex]);
            duckView.setScaleX(-1);
        } else if (speedX < 0 && speedY > 0) {
            negativeYSpeedImageIndex = 0;
            duckView.setImage(duckImages[negativeYSpeedImageIndex]);
            duckView.setScaleX(-1);
        } else if (speedX > 0 && speedY < 0) {
            imageIndex = 0;
            duckView.setImage(duckImages[imageIndex]);
            duckView.setScaleX(1);
        } else {
            negativeYSpeedImageIndex = 0;
            duckView.setImage(duckImages[negativeYSpeedImageIndex]);
            duckView.setScaleX(1);
        }
    }

    //They can collide but sometimes they stuck!!!
    public boolean isColliding(Duck other){
        double thisWidth = this.duckView.getImage().getWidth();
        double thisHeight = this.duckView.getImage().getHeight();
        double otherWidth = other.duckView.getImage().getWidth();
        double otherHeight = other.duckView.getImage().getHeight();

        boolean xOverlap = this.x < other.x + otherWidth && this.x + thisWidth > other.x;
        boolean yOverlap = this.y < other.y + otherHeight && this.y + thisHeight > other.y;

        return xOverlap && yOverlap;
    }

    public void changeDirection(Duck other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;

        double pushFactor = 5;
        this.x += dx > 0 ? pushFactor : -pushFactor;
        this.y += dy > 0 ? pushFactor : -pushFactor;

        speedX *= -1;
        speedY *= -1;
        other.speedX *= -1;
        other.speedY *= -1;

        setImageAfterCollision();
        other.setImageAfterCollision();
    }
}
