import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bleen {

    private static final int SPEED = 2;

    private int x, y;
    private BleenColor color;
    private Image image;
    private boolean offScreen;

    private int pathPointer;
    private ArrayList<Pair> path;

    public Bleen(BleenColor col) {

        makePath();

        color = col;

        switch (color) {
            case RED: image = new Image("file:images/bleens/red.png"); break;
            case BLUE: image = new Image("file:images/bleens/blue.png"); break;
            case GREEN: image = new Image("file:images/bleens/green.png"); break;
            case YELLOW: image = new Image("file:images/bleens/yellow.png"); break;
            case ORANGE: image = new Image("file:images/bleens/orange.png"); break;
            case PURPLE: image = new Image("file:images/bleens/purple.png"); break;
        }

        offScreen = false;

    }

    public boolean isOffScreen() { return offScreen; }

    public void show(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    public void update() {
        if (pathPointer < path.size()) {
            x += path.get(pathPointer).x;
            y += path.get(pathPointer).y;
            pathPointer++;
        } else {
            offScreen = true;
        }
    }

    private void makePath() {

        Scanner fileScan;
        try {
            fileScan = new Scanner(new File("data/levels/" + GameWindow.LEVEL + ".txt"));
        } catch (Exception ex) {
            System.out.println("Error loading level");
            return;
        }

        String start = fileScan.nextLine();
        int openIndex = start.indexOf('(');
        int commaIndex = start.indexOf(',');
        int closeIndex = start.indexOf(')');
        String firstNumStr = start.substring(openIndex + 1, commaIndex);
        String secondNumStr = start.substring(commaIndex + 2, closeIndex);
        int startX = Integer.parseInt(firstNumStr);
        int startY = Integer.parseInt(secondNumStr);

        x = startX * GameWindow.TILE_SIZE - GameWindow.TILE_SIZE;
        y = startY * GameWindow.TILE_SIZE - 6;

        int framesPerTile = GameWindow.TILE_SIZE / SPEED;

        path = new ArrayList<Pair>();
        pathPointer = 0;

        boolean firstTime = true;

        while (fileScan.hasNext()) {

            String dir = fileScan.nextLine();

            int spaceIndex = dir.indexOf(' ');
            String direction = dir.substring(0, spaceIndex);
            String lenStr = dir.substring(spaceIndex + 1);
            int len = Integer.parseInt(lenStr);

            Pair pair;
            switch (direction) {
                case "Up": pair = new Pair(0, -SPEED); break;
                case "Down": pair = new Pair(0, SPEED); break;
                case "Left": pair = new Pair(-SPEED, 0); break;
                case "Right": pair = new Pair(SPEED, 0); break;
                default: pair = new Pair(0, 0); break;
            }

            for (int i = 0; i < framesPerTile * len; i++) {
                path.add(new Pair(pair.x, pair.y));
            }

            if (!fileScan.hasNext() || firstTime) {
                firstTime = false;
                for (int i = 0; i < framesPerTile; i++) {
                    path.add(new Pair(pair.x, pair.y));
                }
            }

        }

    }

}
