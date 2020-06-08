import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class CalculatorClient {

    private final URI uri;
   // private WebTarget webTarget;

    public CalculatorClient() {
        uri = URI.create("http://localhost:8080");

    }

    public double add(double arg1, double arg2) {
        ///CalculatorServer/rest/api/sub/1/2
        return call(arg1, arg2,"add");
    }
    public double sub(double arg1, double arg2) {
        ///CalculatorServer/rest/api/sub/1/2
        return call(arg1, arg2,"sub");
    }
    public double mul(double arg1, double arg2) {
        ///CalculatorServer/rest/api/sub/1/2
        return call(arg1, arg2,"mul");
    }
    public double div(double arg1, double arg2) {
        ///CalculatorServer/rest/api/sub/1/2
        return call(arg1, arg2,"div");
    }
    public double sqrt(double arg) {
        ///CalculatorServer/rest/api/sub/1/2
        return call(arg,"sqrt");
    }
    public double neg(double arg) {
        ///CalculatorServer/rest/api/sub/1/2
        return call(arg,"neg");
    }

    private double call(double arg, String oper) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = getWebTarget(arg, client,oper);

        String response = webTarget.request().accept(MediaType.APPLICATION_JSON)
                .get().readEntity(String.class);
        JSONObject obj = new JSONObject(response);
        double val = obj.getJSONObject("Result").getDouble("value");
        return val;
    }

    private double call(double arg1, double arg2,String oper) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = getWebTarget(arg1, arg2, client,oper);

        String response = webTarget.request().accept(MediaType.APPLICATION_JSON)
                .get().readEntity(String.class);
        JSONObject obj = new JSONObject(response);
        double val = obj.getJSONObject("Result").getDouble("value");
        return val;
    }

    private WebTarget getWebTarget(double arg1, double arg2, Client client,String oper) {
        return getWebTarget(client).path(oper)
                    .path(String.valueOf(arg1))
                    .path(String.valueOf(arg2));
    }
    private WebTarget getWebTarget(double arg1, Client client,String oper) {
        return getWebTarget(client).path(oper)
                .path(String.valueOf(arg1));
    }

    private WebTarget getWebTarget(Client client) {
        WebTarget webTarget = client.target(uri);
        webTarget = webTarget.path("CalculatorServer").path("rest").path("api");
        return webTarget;
    }
}
