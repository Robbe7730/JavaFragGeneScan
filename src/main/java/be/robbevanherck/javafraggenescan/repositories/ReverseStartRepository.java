package be.robbevanherck.javafraggenescan.repositories;

/**
 * Repository for the train/start1 file
 */
public class ReverseStartRepository extends StartEndRepository {
    /**
     * Create a new ReverseStartRepository
     */
    public ReverseStartRepository() {
        super("train/start1");
    }

    private static ReverseStartRepository instance;

    /**
     * Create a new instance of this Repository
     */
    public static void createInstance() {
        instance = new ReverseStartRepository();
    }

    public static ReverseStartRepository getInstance() {
        return instance;
    }
}
