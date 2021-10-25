package indi.jl.sp.log;

import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest extends BaseSpringTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void log() {
        LOGGER.info("test");
    }

    @Test
    public void errorLog() throws InterruptedException {
        LOGGER.error("test");
        Thread.sleep(3000);
    }
}
