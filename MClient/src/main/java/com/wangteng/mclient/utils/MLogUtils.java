package com.wangteng.mclient.utils;

import com.wangteng.mclient.core.MLogAdaptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;


public class MLogUtils {
    public static Logger logger;

    private static Logger createLogger(String path) {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext();
        final Configuration config = ctx.getConfiguration();
        String fileName = config.
                getStrSubstitutor().replace(path);

        PatternLayout layout = PatternLayout.newBuilder()
                .withConfiguration(ctx.getConfiguration())
                .withPattern("%m%n").build();

        Appender fileAppender = FileAppender.newBuilder()
                .setLayout(layout)
                .withFileName(fileName)
                .setName("pattern")
                .build();
        fileAppender.start();

        Appender consoleAppender =  ConsoleAppender.createAppender(layout, null, null, "CONSOLE_APPENDER", null, null);
        consoleAppender.start();

        AppenderRef ref= AppenderRef.createAppenderRef("CONSOLE_APPENDER",null,null);
        AppenderRef ref2 = AppenderRef.createAppenderRef("FILE_APPENDER", null, null);
        AppenderRef[] refs = new AppenderRef[] {ref, ref2};
        LoggerConfig loggerConfig= LoggerConfig.createLogger("false", Level.INFO,"CONSOLE_LOGGER","com",refs,null,config,null);
        loggerConfig.addAppender(consoleAppender,null,null);
        loggerConfig.addAppender(fileAppender, null, null);

        config.addAppender(consoleAppender);
        config.addLogger("com", loggerConfig);
        ctx.updateLoggers(config);

        return LogManager.getContext().getLogger("com");
    }

    public static void log(MLogAdaptor mLogAdaptor,String message) {
        System.out.println(mLogAdaptor.getLogPath());
        logger = createLogger(mLogAdaptor.getLogPath());
        System.out.println("mess:"+message);
        logger.info(message);
    }

    public static void main(String[] args) {
        logger = createLogger("D:/MClientFramework/MClient/info.log");
        logger.info("This is info message!");
    }
}
