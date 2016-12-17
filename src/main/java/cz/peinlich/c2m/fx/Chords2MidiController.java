package cz.peinlich.c2m.fx;

import cz.peinlich.c2m.midi.Chord;
import cz.peinlich.c2m.midi.MidiGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.nio.file.Paths;

/**
 * @author Jiri
 */
public class Chords2MidiController {
    @FXML
    public TextField chords;
    @FXML
    public TextField file;
    @FXML
    private Text actionTarget;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {

        try {
            MidiGenerator generator = new MidiGenerator();
            generator.initialize();

            generator.playChords(Chord.parseChords(chords.getText()));

            generator.closeMidi();
            generator.writeToFile(Paths.get(file.getText()));
            actionTarget.setText("Found " + Chord.parseChords(chords.getText()).size() + " chords");
        } catch (Exception e) {
            e.printStackTrace();
            actionTarget.setText("There was a problem parsing chords...");
        }


    }
}
