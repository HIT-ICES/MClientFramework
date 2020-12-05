package com.wangteng.mclient.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import com.wangteng.mclient.core.MLogAdaptor;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;



public class MLogUtils {
    private static Logger logger = null;

    private static Logger createLogger(String path) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        rollingFileAppender.setContext(loggerContext);
        rollingFileAppender.setAppend(true);
        rollingFileAppender.setName("MLogUtils");
        rollingFileAppender.setFile(path);

        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setFileNamePattern(path+"-%d"+".log");
        rollingPolicy.setMaxHistory(30);
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(rollingFileAppender);
        rollingPolicy.start();
        rollingFileAppender.setRollingPolicy(rollingPolicy);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern("%msg%n");
        encoder.setCharset(Charset.forName("UTF-8"));
        encoder.setContext(loggerContext);
        encoder.start();
        rollingFileAppender.setEncoder(encoder);

        rollingFileAppender.start();

        Logger rootLogger = loggerContext.getLogger("MLogUtils");
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(rollingFileAppender);
        return rootLogger;
    }

    public static void log(MLogAdaptor mLogAdaptor, String mes){
        logger = createLogger(mLogAdaptor.getLogPath());
        logger.info(mes);
    }

        public static void main(String[] args) {
        logger = createLogger("D:/MClientFramework/MClient/info.log");
        logger.info("This is info message!");
    }
}
