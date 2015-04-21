package shield.client.web;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Provides a point of communication with a resource on the shield application
 * server.
 *
 * @author Jeffrey Kabot
 */
public class ServerAccessPoint<T>
{

    //The http client instance shared by all access points.
    private static Client client = null;

    //The URL this access point is bound to.
    private WebTarget target;

    /**
     * Creates a new access point to a resource on the shield schedule server.
     *
     * @param url The url of the web resource.
     */
    public ServerAccessPoint(String url)
    {
        //Create the client if it's not yet been built.
        //Link it to the Jackson message body reader/writer
        if (client == null) {
            client = ClientBuilder.newClient();
            client.register(JacksonJsonProvider.class);
        }
        target = client.target(url);
    }

    /**
     * Executes a GET request on the resource.
     *
     * @return The http response from the server.
     */
    public Response request()
    {
        Response rsp = target.request(MediaType.APPLICATION_JSON).get();
        return rsp;
    }

    /**
     * Executes a POST request on the resource.
     *
     * @param payload The content to submit to the server.
     * @return The http response from the server.
     */
    public Response request(T payload)
    {
        Response rsp = target.request().post(Entity.entity(payload, MediaType.APPLICATION_JSON));
        return rsp;
    }

    /**
     * Closes the http client.
     */
    public static void close()
    {
        client.close();
    }
}
