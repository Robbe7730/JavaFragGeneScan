package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Repository for the data in the 'rgene' file
 */
public class ReverseMatchEmissionRepository extends MatchEmissionRepository {
    private ReverseMatchEmissionRepository() {
        super("train/rgene");
    }

    private static ReverseMatchEmissionRepository instance;

    /**
     * Create a new instance of this Repository
     */
    public static void createInstance() {
        instance = new ReverseMatchEmissionRepository();
    }

    public static ReverseMatchEmissionRepository getInstance() {
        return instance;
    }

    @Override
    protected HMMState matchStateFromInt(int i) {
        switch (i) {
            case 1:
                return HMMState.MATCH_REVERSE_1;
            case 2:
                return HMMState.MATCH_REVERSE_2;
            case 3:
                return HMMState.MATCH_REVERSE_3;
            case 4:
                return HMMState.MATCH_REVERSE_4;
            case 5:
                return HMMState.MATCH_REVERSE_5;
            case 6:
                return HMMState.MATCH_REVERSE_6;
            default:
                return HMMState.NO_STATE;
        }
    }


}
