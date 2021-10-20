
package asteroids.personaggi;

import asteroids.AsteroidsApplicationMain;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Personaggi {

    private Polygon personaggio;
    private Point2D movimento;
    private boolean esiste;

    public Personaggi(Polygon polygon, int x, int y) {
        this.personaggio = polygon;
        this.personaggio.setTranslateX(x);
        this.personaggio.setTranslateY(y);

        this.movimento = new Point2D(0, 0);

        this.esiste = true;
    }

    public Polygon getPersonaggio() {
        return personaggio;
    }

    public Point2D getMovimento() {
        return movimento;
    }

    public void setMovimento(Point2D movimento) {
        this.movimento = movimento;
    }

    public boolean isEsiste() {
        return esiste;
    }

    public void setEsiste(boolean esiste) {
        this.esiste = esiste;
    }

    public void giraSinistra() {
        this.personaggio.setRotate(this.personaggio.getRotate() - 5);
    }

    public void giraDestra() {
        this.personaggio.setRotate(this.personaggio.getRotate() + 5);
    }

    public void muovi() {
        this.personaggio.setTranslateX(this.personaggio.getTranslateX() + this.movimento.getX());
        this.personaggio.setTranslateY(this.personaggio.getTranslateY() + this.movimento.getY());

        if (this.personaggio.getTranslateX() < 0) {
            this.personaggio.setTranslateX(this.personaggio.getTranslateX() + AsteroidsApplicationMain.larghezza);
        }

        if (this.personaggio.getTranslateX() > AsteroidsApplicationMain.larghezza) {
            this.personaggio.setTranslateX(this.personaggio.getTranslateX() % AsteroidsApplicationMain.larghezza);
        }

        if (this.personaggio.getTranslateY() < 0) {
            this.personaggio.setTranslateY(this.personaggio.getTranslateY() + AsteroidsApplicationMain.altezza);
        }

        if (this.personaggio.getTranslateY() > AsteroidsApplicationMain.altezza) {
            this.personaggio.setTranslateY(this.personaggio.getTranslateY() % AsteroidsApplicationMain.altezza);
        }
    }

    public void accellera() {
        double changeX = Math.cos(Math.toRadians(this.personaggio.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.personaggio.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movimento = this.movimento.add(changeX, changeY);
    }

    public boolean collisione(Personaggi other) {
        Shape areaCollisione = Shape.intersect(this.personaggio, other.getPersonaggio());
        return areaCollisione.getBoundsInLocal().getWidth() != -1;
    }
}
