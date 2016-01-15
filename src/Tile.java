
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Celal
 */
public class Tile extends StackPane {

    private final Rectangle rect;
    private boolean alive, goingToDie;

    public Tile() {
        rect = new Rectangle();
        rect.setStrokeWidth(1);
        rect.setStroke(Color.BLACK);
        alive = false;
        goingToDie = false;

        rect.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && alive == true) {
                setAlive(false);
                rect.setFill(Color.GRAY);
                System.out.println(alive);

            } else if (e.getButton() == MouseButton.PRIMARY && alive == false) {
                setAlive(true);
                rect.setFill(Color.BLUE);
                System.out.println(alive);
            }
        });
    }

    public void setGoingToDie(boolean b) {
        goingToDie = b;
    }

    public boolean getGoingToDie() {
        return goingToDie;
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean b) {
        alive = b;
    }

}
