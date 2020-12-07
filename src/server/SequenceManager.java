
package server;

import java.util.Arrays;

/**
 *
 * @author ClaraU
 */
public class SequenceManager {
    
    private char EXPECTED_SEQUENCE[] = new char[] {31, 18, 30};

    private char sequence[] = new char[3];
    
    public boolean addChar(char input) {
        sequence [0] = sequence[1];
        sequence [1] = sequence[2];
        sequence [2] = input;
        return Arrays.equals(sequence, EXPECTED_SEQUENCE);
    }
}
