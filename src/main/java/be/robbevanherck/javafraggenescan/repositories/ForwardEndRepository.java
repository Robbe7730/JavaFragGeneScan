package be.robbevanherck.javafraggenescan.repositories;

/**
 * Repository for the train/stop file
 */
public class ForwardEndRepository extends StartEndRepository {
    /**
     * Create a new ForwardEndRepository
     */
    public ForwardEndRepository() {
        super("train/stop");
    }

    private static ForwardEndRepository instance;

    /**
     * Create a new instance of this Repository
     */
    public static void createInstance() {
        instance = new ForwardEndRepository();
    }

    public static ForwardEndRepository getInstance() {
        return instance;
    }
}
