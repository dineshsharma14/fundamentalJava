import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BeatBox {

    // instance variables
    JFrame theFrame;
    JPanel mainPanel;
    ArrayList<JCheckBox> checkBoxList;// storing checkboxes in an ArrayList
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat",
        "Acoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo",
        "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
        "Low-mid Tom", "High Agogo", "Open Hi Conga"};
    int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

    public static void main(String[] args) {
        new BeatBox().buildGUI(); // straight away calling the setup and initialization method of 
    }                             // app and not making an instance variable to hold the remote!  

    public void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // using enums
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        // Emptyborder gives us a margin between the edges of panel and where
        // components are placed!

        checkBoxList = new ArrayList<JCheckBox>();

        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        upTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton serialize = new JButton("Save");
        serialize.addActionListener(new MySaveListener());
        buttonBox.add(serialize);

        JButton restore = new JButton("Restore");
        restore.addActionListener(new MyReadListener());
        buttonBox.add(restore);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(layout.EAST, buttonBox);// can be layout.EAST ??
        background.add(layout.WEST, nameBox);// changed the LM of Panel which is flow!

        theFrame.getContentPane().add(background);

        // making a grid and adding it on a JPanel - mainPanel
        // and then adding mainPanel on to the other JPanel - background
        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(layout.CENTER, mainPanel);
        // adding checkboxes on to the mainPanel    
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();

        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    public void setUpMidi() {

        try {
            // CD Player    
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            // CD
            sequence = new Sequence(Sequence.PPQ, 4);
            // Single track on CD
            track = sequence.createTrack();
        } catch (Exception e) { e.printStackTrace();
        }
    }

    // Turning check box states into MIDI events and add them to track!

    public void buildTrackAndStart() {
        // a 16 element array to hold all the beat values of 1 instrument, its key or 0.
        int[] trackList = null;

        // get rid of old track
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        // run a loop for each instrument!
        for (int i = 0; i < 16; i++) {

            trackList = new int[16];
            int key = instruments[i];

            // run the loop for each checkbox of that instrument
            for (int j = 0; j < 16; j++) {

                System.out.println(checkBoxList.get(j + (16 * i)));

                JCheckBox jc = checkBoxList.get(j + (16 * i));
                // though object type stored in checkBoxList is already JCheckBox!!

                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }
            // for this instrument and for its all 16 beats make events and add
            // them on to track.
            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }
        // making sure there is an event on beat 16, otherwise beatbox might
        // not go the full 16 beats before start all over again
        track.add(makeEvent(192, 9, 1, 0, 15));

        try {

            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
        } catch (Exception e) {e.printStackTrace();}
    }

    public void makeTracks(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];

            if (key != 0) {
                // make NOTE ON and OFF events for the beat
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a,tick);
        } catch(Exception e) {e.printStackTrace();}

        return event;
    }

    public class MyStartListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            buildTrackAndStart();
        }
    }

    public class MyStopListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            sequencer.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    public class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor *  0.97));
        }
    }

    public class MySaveListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            // a boolean array to keep state of all 256 checkBoxes of total
            // 16 beats of 16 instruments
            boolean[] checkBoxState = new boolean[256];
            for (int i = 0; i < 256; i++) {
                // walk through the checkBoxList (ArrayList of checkBoxes),and
                // get the state of each one and add it to the boolean array.
                JCheckBox check = checkBoxList.get(i);
                if (check.isSelected()) {
                    checkBoxState[i] = true;
                }
            }

            try {
                FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));
                ObjectOutputStream os = new ObjectOutputStream(fileStream);
                os.writeObject(checkBoxState);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class MyReadListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {

            boolean[] checkBoxState = null;
            try {
                FileInputStream fileIn = new FileInputStream(new File("Checkbox.ser"));
                ObjectInputStream is = new ObjectInputStream(fileIn);
                // read the single object in the file (boolean array) and cast it back to
                // a boolean array 
                checkBoxState = (boolean[]) is.readObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // now restore the state of each of checkboxes in the ArrayList of actual
            // JCheckBox objects in checkBoxList!
            for (int i = 0; i < 256; i++) {
                JCheckBox check = checkBoxList.get(i);
                if (checkBoxState[i]) {
                    check.setSelected(true);
                } else {
                    check.setSelected(false);
                }
            }

            sequencer.stop();
            buildTrackAndStart();
            
        }
    }

}
    

        
            
        














        
        

    
