package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Chronometer {

    private static final Integer STARTTIME = 0;
    private static final Integer MINUTESINANHOUR = 59;
    private static final Integer SECONDSINAMINUTE = 59;

    private Timeline timeline;

    private Integer timeSeconds = STARTTIME;
    private Integer timeMinutes  = STARTTIME;
    private Integer timeHours = STARTTIME;


    /** Permet de relancer le chronomètre en partant de 0*/
    private void restart() {

        timeline.playFromStart();
    }

    /** Permet de mettre en pause les timings et donc le chronomètre */
    private void pause() {
        timeline.pause();
    }

    /** Permet de play le chronomètre */
    private void play() {
        timeline.play();
    }
}
