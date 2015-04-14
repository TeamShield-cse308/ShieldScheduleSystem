/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import static javax.json.stream.JsonParser.Event.END_OBJECT;
import static javax.json.stream.JsonParser.Event.KEY_NAME;
import static javax.json.stream.JsonParser.Event.START_OBJECT;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import shield.shared.dto.School;

/**
 * A class to read the JSON detailing the school objects in the database
 * Adapted from: 
 * http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/javafx_json_tutorial/javafx_javaee7_json_tutorial.html#section2s1
 * @author Jeffrey Kabot
 */
@Provider
@Consumes({"application/json"})
public class SchoolsBodyReader implements MessageBodyReader<List<School>>
{

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns,
            MediaType mt)
    {
        return true;
    }

    @Override
    public List<School> readFrom(Class<List<School>> type, Type type1, Annotation[] antns,
            MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException
    {
        if (mt.getType().equals("application") && mt.getSubtype().equals("json")) {
            School sch = new School();
            List<School> schools = new ArrayList();
            JsonParser parser = Json.createParser(in);
            while (parser.hasNext()) {
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_OBJECT:
                        sch = new School();
                        break;
                    case END_OBJECT:
                        schools.add(sch);
                        break;
                    case KEY_NAME:
                        String key = parser.getString();
                        parser.next();
                        switch (key) {
                            case "schoolName":
                                sch.name = parser.getString();
                                break;
                            case "numPeriods":
                                sch.numPeriods = parser.getString();
                                break;
                            case "numSemesters":
                                sch.numSemesters = parser.getString();
                                break;
                            case "numScheduleDays":
                                sch.numScheduleDays = parser.getString();
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
            return schools;
        }
        throw new UnsupportedOperationException("Not supported MediaType: " + mt);
    }
}
