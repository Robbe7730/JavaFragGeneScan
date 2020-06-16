package be.robbevanherck.javafraggenescan.repositories;

/**
 * Repository for the train/stop1 file
 */
public class ReverseEndRepository extends StartEndRepository {
    /**
     * Create a new ReverseEndRepository
     */
    public ReverseEndRepository() {
        super("train/stop1");
    }

    private static ReverseEndRepository instance;

    /**
     * Create a new instance of this Repository
     */
    public static void createInstance() {
        instance = new ReverseEndRepository();
    }

    public static ReverseEndRepository getInstance() {
        return instance;
    }
}
