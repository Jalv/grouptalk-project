package edu.upc.eetac.dsa.grouptalk;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by juan on 26/10/15.
 */
public class GrouptalkResourceConfig extends ResourceConfig {

    public GrouptalkResourceConfig() {

        packages("edu.upc.eetac.dsa.grouptalk");
    }
}
