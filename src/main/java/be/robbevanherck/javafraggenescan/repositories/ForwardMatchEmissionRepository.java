package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.entities.HMMState;

/**
 * Repository for the data in the 'gene' file
 */
public class ForwardMatchEmissionRepository extends MatchEmissionRepository {
    private ForwardMatchEmissionRepository() {
        super("train/gene");
    }

    @Override
    public HMMState matchStateFromInt(int x) {
        switch (x) {
            case 1:
                return HMMState.MATCH_1;
            case 2:
                return HMMState.MATCH_2;
            case 3:
                return HMMState.MATCH_3;
            case 4:
                return HMMState.MATCH_4;
            case 5:
                return HMMState.MATCH_5;
            case 6:
                return HMMState.MATCH_6;
            default:
                return HMMState.NO_STATE;
        }
    }

    private static ForwardMatchEmissionRepository instance;

    /**
     * Create a new instance of this Repository
     */
    public static void createInstance() {
        instance = new ForwardMatchEmissionRepository();
    }

    public static ForwardMatchEmissionRepository getInstance() {
        return instance;
    }
}
