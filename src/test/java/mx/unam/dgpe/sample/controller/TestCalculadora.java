package mx.unam.dgpe.sample.controller;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import io.vertx.core.AbstractVerticle;
import static mx.unam.dgpe.sample.controller.RestUtil.*;

public class TestCalculadora extends AbstractVerticle {
    private static final Logger logger = Logger.getLogger(TestCalculadora.class);
    
    @Test
    public void ok() throws Exception {
//Nota Para el tester hay que configurar la ip del servidor

        String result = sendGet("http://132.248.39.36:8080/calc/suma?a=4&b=1");
        assertTrue("Suma",result.contains("\"resultado\" : 5")==true);
        logger.info(result);

        result = sendGet("http://132.248.39.36:8080/calc/resta?a=4&b=1");
        assertTrue("Resta",result.contains("\"resultado\" : 3")==true);
        logger.info(result);

        result = sendGet("http://132.248.39.36:8080/calc/mult?a=4&b=1");
        assertTrue("Mult",result.contains("\"resultado\" : 4")==true);
        logger.info(result);

        result = sendGet("http://132.248.39.36:8080/calc/div?a=4&b=1");
        assertTrue("Div",result.contains("\"resultado\" : 4")==true);
        logger.info(result);
    }

}
