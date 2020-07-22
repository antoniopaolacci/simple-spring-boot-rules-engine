package it.sample.rulesengine.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	private static final Logger log = LoggerFactory.getLogger(ApplicationTest.class);
	
    @Test
    public void testPerformSomeTask() throws Exception {
    	
    	log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        log.info("testPerformSomeTask() called;");
     
    }
    
}
