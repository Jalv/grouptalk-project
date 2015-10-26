package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.GroupAlreadyExistsException;
import edu.upc.eetac.dsa.grouptalk.dao.GroupDAO;
import edu.upc.eetac.dsa.grouptalk.dao.GroupDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.Group;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by juan on 26/10/15.
 */
@Path("groups")
public class GroupResource {
    @Context
    private SecurityContext securityContext;
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void registerGroup(@FormParam("name") String name, @Context UriInfo uriInfo) throws URISyntaxException {
        boolean a= securityContext.isUserInRole("admin");
        if (a==true) {
            if (name == null)
                throw new BadRequestException("all parameters are mandatory");
            GroupDAO groupDAO = new GroupDAOImpl();
            Group group = null;
            try {
                group = groupDAO.createGroup(name);
            } catch (GroupAlreadyExistsException e) {
                throw new WebApplicationException("group name already exists", Response.Status.CONFLICT);
            } catch (SQLException e) {
                throw new InternalServerErrorException();
            }
            URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + group.getId());
        }
        else
         throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }

}
