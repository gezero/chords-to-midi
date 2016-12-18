package cz.peinlich.c2m.fx;

import cz.peinlich.c2m.midi.Chord;
import cz.peinlich.c2m.midi.ChordName;
import cz.peinlich.c2m.midi.MidiGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
            List<Chord> chords = ChordName.parseChords(this.chords.getText()).stream().map(Chord::new).collect(Collectors.toList());
            generator.playChords(chords);

            generator.closeMidi();
            generator.writeToFile(Paths.get(file.getText()));
            actionTarget.setText("Found " + chords.size() + " chords");
        } catch (Exception e) {
            e.printStackTrace();
            actionTarget.setText("There was a problem parsing chords...");
        }


    }
}
