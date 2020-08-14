package be.robbevanherck.javafraggenescan.entities;

import java.util.List;

/**
 * The sentinel ViterbiInput indicating that the input is done and threads can begin stopping
 */
public class EOFViterbiInput extends ViterbiInput {
    /**
     * Create a new EOFViterbiInput
     */
    public EOFViterbiInput() {
        super("EOF", List.of());
    }

    @Override
    public boolean isEOF() {
        return true;
    }
}
