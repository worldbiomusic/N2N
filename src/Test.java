import java.io.IOException;
import java.util.logging.*;

public class Test {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Test.class.getName());
        System.out.println("Start");
        logger.setLevel(Level.INFO);
        logger.info("log1");

        try {
            Handler fileHandler = new FileHandler("logs.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.info("log2-");
            logger.info("log3");
            logger.info("log4");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Logger.GLOBAL_LOGGER_NAME);
        System.out.println(LogManager.getLogManager().getLoggerNames());
    }
}
