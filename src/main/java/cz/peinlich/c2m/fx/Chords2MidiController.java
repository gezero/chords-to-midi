package cz.peinlich.c2m.fx;

import cz.peinlich.c2m.midi.Chord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.List;

/**
 * @author Jiri
 */
public class Chords2MidiController {
    @FXML
    public TextField chords;
    @FXML
    private Text actionTarget;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        List<Chord> parsedChords = Chord.parseChords(chords.getText());
        actionTarget.setText("Found " + parsedChords.size() + " chords");
    }
}
