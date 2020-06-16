package be.robbevanherck.javafraggenescan.repositories;

/**
 * Repository for the train/start file
 */
public class ForwardStartRepository extends StartEndRepository {
    /**
     * Create a new ForwardStartRepository
     */
    public ForwardStartRepository() {
        super("train/start");
    }

    private static ForwardStartRepository instance;

    /**
     * Create a new instance of this Repository
     */
    public static void createInstance() {
        instance = new ForwardStartRepository();
    }

    public static ForwardStartRepository getInstance() {
        return instance;
    }
}
