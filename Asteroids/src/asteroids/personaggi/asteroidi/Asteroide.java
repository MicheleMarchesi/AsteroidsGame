
package asteroids.personaggi.asteroidi;

import asteroids.personaggi.Personaggi;
import java.util.Random;
import javafx.scene.shape.Polygon;

public class Asteroide extends Personaggi {

    private double rotationalMovement;

    public Asteroide(int x, int y) {
        super(new GeneratoreFigure().createPolygon(), x, y);

        Random rnd = new Random();

        super.getPersonaggio().setRotate(rnd.nextInt(360));

        int accelerationAmount = 1 + rnd.nextInt(10);
        for (int i = 0; i < accelerationAmount; i++) {
            accellera();
        }

        this.rotationalMovement = 0.5 - rnd.nextDouble();
    }

    @Override
    public void muovi() {
        super.muovi();
        super.getPersonaggio().setRotate(super.getPersonaggio().getRotate() + rotationalMovement);
    }
}
