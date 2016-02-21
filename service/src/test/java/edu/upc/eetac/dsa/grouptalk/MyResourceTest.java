package edu.upc.eetac.dsa.grouptalk;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MyResourceTest {



    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void test_demo(){
        assertEquals("Test correcto en jenkins","Test correcto en jenkins");
    }


}
