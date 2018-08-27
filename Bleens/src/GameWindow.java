import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameWindow {

    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;

    public static final int TILE_SIZE = 32;
    public static final int LEVEL = 1;

    private int frameCount;

    private ArrayList<Bleen> bleens;

    public GameWindow(Stage myStage) {

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image map = new Image("file:images/levels/" + LEVEL + ".png");

        bleens = new ArrayList<Bleen>();

        frameCount = 0;

        // Game loop - runs at (about) 60fps
        new AnimationTimer() {
            public void handle(long nano) {
                gc.drawImage(map, 0, 0);
                for (Bleen bleen : bleens) {
                    bleen.show(gc);
                    bleen.update();
                }
                if (frameCount % 30 == 0) {
                    bleens.add(new Bleen(getRandomBleenColor()));
                }
                frameCount++;
                if (bleens.get(0).isOffScreen()) bleens.remove(0);
            }
        }.start();

        myStage.setTitle("Bleens");
        myStage.setScene(scene);
        myStage.show();

    }

    private BleenColor getRandomBleenColor() {
        int rand = (int) (Math.random() * 6);
        switch (rand) {
            case 0: return BleenColor.RED;
            case 1: return BleenColor.BLUE;
            case 2: return BleenColor.GREEN;
            case 3: return BleenColor.YELLOW;
            case 4: return BleenColor.ORANGE;
            case 5: return BleenColor.PURPLE;
        }
        return null;
    }

}
