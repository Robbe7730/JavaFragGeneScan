package be.robbevanherck.javafraggenescan.entities;

import be.robbevanherck.javafraggenescan.enums.StateTransition;
import be.robbevanherck.javafraggenescan.exceptions.ConfigReadException;

import java.io.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static be.robbevanherck.javafraggenescan.enums.StateTransition.NUM_STATE_TRANSITIONS;

/**
 * Class representing the configuration of the model
 */
public class ModelConfig {
    private final Map<StateTransition, Float> stateTransitions;

    private static final Logger LOGGER = Logger.getLogger(ModelConfig.class.getName());

    /**
     * Read the config from a file
     * @param file The file to be read
     */
    public ModelConfig(File file) {
        stateTransitions = new EnumMap<>(StateTransition.class);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Scanner s = new Scanner(br);

            readStateTransitions(s);
        } catch (FileNotFoundException fnfe) {
            LOGGER.log(Level.SEVERE, "No such file {0}, nothing read", file.getAbsolutePath());
            throw new ConfigReadException("File not found", fnfe);
        } catch (IOException ioException) {
            throw new ConfigReadException("Could not read file", ioException);
        }
    }


    /**
     * Read the state transitions from the file.
     * @param s The Scanner to use
     */
    private void readStateTransitions(Scanner s) {
        // The first line is "Transition=", so we don't care about the contents, but we do check if it is null to throw an exception
        String line = s.nextLine();
        if (line == null) {
            throw new ConfigReadException("Could not read Transition section of the State Transitions");
        }

        // Read all the state transitions
        for (int i = 0; i < NUM_STATE_TRANSITIONS; i++) {
            StateTransition transition = StateTransition.fromString(s.next());
            float weight = s.nextFloat();
            LOGGER.log(Level.FINE, "Adding weight {0} to transition {1}", new Object[]{transition, weight});
            stateTransitions.put(transition, weight);
        }
    }

    /**
     * Read the
     */
}
