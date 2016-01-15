
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Celal
 */
public class Main extends Application {

    private static Tile[][] tiles = new Tile[14][14]; //13 because outofboundsexecption
    private AnimationTimer loop;
    private Button stop, play;

    @Override
    public void start(Stage primaryStage) throws Exception {

        play = new Button("Play");
        play.setOnAction((ActionEvent e) -> {
            play.setVisible(false);
            stop.setVisible(true);

            loop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    try {
                        callFunctionWithDelay();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            loop.start();
        });
        
        play.setTranslateY(545);
        play.setPrefWidth(600);
        play.setPrefHeight(100);
        play.setStyle("-fx-font-size: 50px;\n"
                + "  -fx-font-family: \"Comic Sans MS\";\n"
                + " -fx-background-color: orange;\n"
                + "-fx-text-fill: black;");

        stop = new Button("Stop");
        stop.setVisible(false);
        stop.setOnAction(e -> {
            stop.setVisible(false);
            play.setVisible(true);
            loop.stop();
        });

        stop.setTranslateY(438);
        stop.setPrefWidth(600);
        stop.setPrefHeight(100);
        stop.setStyle("-fx-font-size: 50px;\n"
                + "  -fx-font-family: \"Comic Sans MS\";\n"
                + " -fx-background-color: orange;\n"
                + "-fx-text-fill: black;");
        
        VBox box = new VBox(3);
        box.getChildren().addAll(createContent(), play, stop);
        Scene scene = new Scene(box, 600, 700);

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public synchronized void callFunctionWithDelay() throws InterruptedException {
        rule1();
        amountOfDeaths();
        Thread.sleep(100);
    }

    public Parent createContent() {

        Pane root = new Pane();

        for (int x = 1; x < 13; x++) {
            for (int y = 1; y < 13; y++) {
                Tile tile = new Tile();
                tile.getRect().setWidth(50);
                tile.getRect().setHeight(50);
                tile.getRect().setFill(Color.GRAY);
                tile.getRect().setTranslateX(x * 50 - 50);
                tile.getRect().setTranslateY(y * 50 - 50);
                tiles[x][y] = tile;
                root.getChildren().addAll(tile.getRect());
            }
        }
        return root;
    }

    public void rule1() {
        for (int x = 1; x < 13; x++) {
            for (int y = 1; y < 13; y++) { //looping through all the tiles / beings
                Tile being = tiles[x][y];

                //get all the beings around him
                Tile one = tiles[x - 1][y - 1];
                Tile two = tiles[x - 1][y];
                Tile three = tiles[x - 1][y + 1];
                Tile four = tiles[x][y - 1];
                Tile five = tiles[x][y + 1];
                Tile six = tiles[x + 1][y - 1];
                Tile seven = tiles[x + 1][y];
                Tile eight = tiles[x + 1][y + 1];

                ArrayList<Tile> everything = new ArrayList(); //add everything together, including null values
                Collections.addAll(everything, one, two, three, four, five, six, seven, eight);

                ArrayList<Tile> around = new ArrayList(); //disregard the null values: so no nullpointerexception
                for (Tile t : everything) {
                    if (t != null) {
                        around.add(t);
                    }
                }

                int alive = 0;

                for (Tile tile : around) {
                    if (tile.getAlive()) {
                        alive++; //how many beings are alive next to him
                    }
                }

                if (being.getAlive()) {           //if this being is alive
                    if (alive < 2) {
                        being.setGoingToDie(true);
                    } else if (alive >= 4) {
                        being.setGoingToDie(true);
                    } else if (alive >= 2 && alive < 4) {
                        being.setGoingToDie(false);
                    }
                } else if (!being.getAlive()) {     //if this being is dead
                    if (alive == 3) {
                        being.setGoingToDie(false);
                    } else {
                        being.setGoingToDie(true);
                    }
                }
            }
        }
    }

    public void amountOfDeaths() {
        for (int x = 1; x < 13; x++) {
            for (int y = 1; y < 13; y++) { //looping through all the tiles / beings
                Tile being = tiles[x][y];
                if (being.getGoingToDie()) {
                    being.setAlive(false);
                    being.getRect().setFill(Color.GRAY);
                } else {
                    being.setAlive(true);
                    being.getRect().setFill(Color.BLUE);
                }
            }
        }
    }

}
