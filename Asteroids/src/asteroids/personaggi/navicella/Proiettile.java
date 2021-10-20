
package asteroids.personaggi.navicella;

import asteroids.personaggi.Personaggi;
import javafx.scene.shape.Polygon;

public class Proiettile extends Personaggi {

    public Proiettile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }

}