package com.example.utils;
import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class LogUtils {
	/*注意：LogUtils类中LogManager.getLogger中的参数testTime应该和log4j2.xml中Logger name的名称完全一样。*/
	static private Logger logger = LogManager.getLogger("testTime");

    public static void trace(Object object) {
        logger.trace(object);
    }

    public static void debug(Object object) {
        logger.debug(object);
    }

    public static void info(Object object) {
        logger.info(object);
    }

    public static void warn(Object object) {
        logger.warn(object);
    }

    public static void error(Object object) {
        logger.error(object);
    }

    public static void fatal(Object object) {
        logger.fatal(object);
    }


}
