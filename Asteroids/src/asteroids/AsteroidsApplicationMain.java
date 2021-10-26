package asteroids;

import asteroids.personaggi.asteroidi.Asteroide;
import asteroids.personaggi.navicella.Navicella;
import asteroids.personaggi.navicella.Proiettile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AsteroidsApplicationMain extends Application {

    public static int larghezza = 600;
    public static int altezza = 400;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pannello = new Pane();
        Text testoPunti = new Text(10, 20, "Points: 0");

        AtomicInteger punti = new AtomicInteger();

        pannello.setPrefSize(larghezza, altezza);

        Navicella naveGiocatore = new Navicella(larghezza / 2, altezza / 2);
        List<Proiettile> proiettili = new ArrayList<>();
        List<Asteroide> asteroidi = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroide asteroideDaAggiungere = new Asteroide(rnd.nextInt(larghezza / 3), rnd.nextInt(altezza));
            asteroidi.add(asteroideDaAggiungere);
        }

        pannello.getChildren().add(testoPunti);
        pannello.getChildren().add(naveGiocatore.getPersonaggio());
        asteroidi.forEach(asteroide -> pannello.getChildren().add(asteroide.getPersonaggio()));

        Scene scena = new Scene(pannello);

        Map<KeyCode, Boolean> tastiPremuti = new HashMap<>();

        scena.setOnKeyPressed(event -> {
            tastiPremuti.put(event.getCode(), Boolean.TRUE);
        });

        scena.setOnKeyReleased(event -> {
            tastiPremuti.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer() {

            @Override
            public void handle(long now) {

                if (Math.random() < 0.005) {
                    Asteroide asteroid = new Asteroide(larghezza, altezza);
                    if (!asteroid.collisione(naveGiocatore)) {
                        asteroidi.add(asteroid);
                        pannello.getChildren().add(asteroid.getPersonaggio());
                    }
                }
                if (tastiPremuti.getOrDefault(KeyCode.LEFT, false)) {
                    naveGiocatore.giraSinistra();
                }

                if (tastiPremuti.getOrDefault(KeyCode.RIGHT, false)) {
                    naveGiocatore.giraDestra();
                }

                if (tastiPremuti.getOrDefault(KeyCode.UP, false)) {
                    naveGiocatore.accellera();
                }
                if (tastiPremuti.getOrDefault(KeyCode.SPACE, false) && proiettili.size() < 3) {

                    Proiettile projectile = new Proiettile((int) naveGiocatore.getPersonaggio().getTranslateX(), (int) naveGiocatore.getPersonaggio().getTranslateY());
                    projectile.getPersonaggio().setRotate(naveGiocatore.getPersonaggio().getRotate());
                    proiettili.add(projectile);

                    projectile.accellera();
                    projectile.setMovimento(projectile.getMovimento().normalize().multiply(3));

                    pannello.getChildren().add(projectile.getPersonaggio());
                }

                naveGiocatore.muovi();
                asteroidi.forEach(asteroide -> asteroide.muovi());
                proiettili.forEach(projectile -> projectile.muovi());

                asteroidi.forEach(asteroide -> {
                    if (naveGiocatore.collisione(asteroide)) {
                        stop();
                    }
                });

                proiettili.forEach(projectile -> {
                    asteroidi.forEach(asteroid -> {
                        if (projectile.collisione(asteroid)) {
                            projectile.setEsiste(false);
                            asteroid.setEsiste(false);
                        }
                    });
                    if (!projectile.isEsiste()) {
                        testoPunti.setText("Points: " + punti.addAndGet(1000));
                    }
                });

                proiettili.stream()
                        .filter(projectile -> !projectile.isEsiste())
                        .forEach(projectile -> pannello.getChildren().remove(projectile.getPersonaggio()));
                proiettili.removeAll(proiettili.stream()
                        .filter(projectile -> !projectile.isEsiste())
                        .collect(Collectors.toList()));

                asteroidi.stream()
                        .filter(asteroid -> !asteroid.isEsiste())
                        .forEach(asteroid -> pannello.getChildren().remove(asteroid.getPersonaggio()));
                asteroidi.removeAll(asteroidi.stream()
                        .filter(asteroid -> !asteroid.isEsiste())
                        .collect(Collectors.toList()));

            }
        }.start();
        stage.setTitle("Asteroids Game");
        stage.setScene(scena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(AsteroidsApplicationMain.class);
    }    
}
