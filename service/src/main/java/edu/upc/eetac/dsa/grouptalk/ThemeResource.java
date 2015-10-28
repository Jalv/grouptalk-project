package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.*;
import edu.upc.eetac.dsa.grouptalk.entity.Group;
import edu.upc.eetac.dsa.grouptalk.entity.Theme;
import edu.upc.eetac.dsa.grouptalk.entity.ThemeCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by juan on 28/10/15.
 */
@Path("themes")
public class ThemeResource {

    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createTheme(@FormParam("namegroup") String namegroup , @FormParam("subject") String subject, @FormParam("content") String content, @Context UriInfo uriInfo) throws URISyntaxException {
        if(namegroup==null || subject==null || content == null)
            throw new BadRequestException("all parameters are mandatory");
        String userid = securityContext.getUserPrincipal().getName();
        GroupDAO groupDAO = new GroupDAOImpl();
        Group group;
        try{
            group = groupDAO.getGroupByName(namegroup);
        }catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        ThemeDAO themeDAO = new ThemeDAOImpl();

        Theme theme = null;

        try {
            theme = themeDAO.createTheme(userid,group.getId(),subject,content);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        catch (UserDidntSubscribedException e) {
            throw new ForbiddenException("NOP");
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + theme.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_THEME).entity(theme).build();
    }

    @Path("/{id}")
    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_THEME)
    public Theme getTheme(@PathParam("id") String id) {
        Theme theme = null;
        try {
            theme = (new ThemeDAOImpl()).getThemeById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }catch (UserDidntSubscribedException e) {
            throw new ForbiddenException("NOP");
        }
        if (theme == null)
            throw new NotFoundException("Group with id = " + id + " doesn't exist");
        return theme;
    }



    @Path("/{userid}/{namegroup}")
    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_THEME_COLLECTION)
    public ThemeCollection getThemesByGroupId(@PathParam("namegroup") String namegroup,@PathParam("userid") String userid) {

        String id = securityContext.getUserPrincipal().getName();
        if(!userid.equals(id))
            throw new ForbiddenException("operation not allowed");
        GroupDAO groupDAO = new GroupDAOImpl();
        Group group;
        try{
            group = groupDAO.getGroupByName(namegroup);
        }catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        ThemeCollection themes = null;
        try {
            themes = (new ThemeDAOImpl().getThemesByGroupId(group.getId()));
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        } catch (UserDidntSubscribedException e) {
            e.printStackTrace();
        }
        if (themes == null)
            throw new NotFoundException("No themes found");
        return themes;
    }














}
