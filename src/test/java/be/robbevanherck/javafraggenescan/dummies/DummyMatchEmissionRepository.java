package be.robbevanherck.javafraggenescan.dummies;

import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.repositories.MatchEmissionRepository;

public class DummyMatchEmissionRepository extends MatchEmissionRepository {
    public DummyMatchEmissionRepository() {
        // This file is unused, so the contents don't matter
        super("train/gene");
    }

    @Override
    protected HMMState matchStateFromInt(int i) {
        switch (i) {
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
}
