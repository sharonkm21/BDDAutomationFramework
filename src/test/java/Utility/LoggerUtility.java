package Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtility
{
    // Instance not created until it's requested (lazy initialization)
    private static Logger logger;

    // Private constructor to prevent instantiation
    private LoggerUtility() { }

    // Synchronized to make it thread-safe
    public static Logger getLogger() {
        if (logger == null) {
            synchronized (LoggerUtility.class) {
                if (logger == null) {
                    logger = LogManager.getLogger(LoggerUtility.class);
                }
            }
        }
        return logger;
    }
}
