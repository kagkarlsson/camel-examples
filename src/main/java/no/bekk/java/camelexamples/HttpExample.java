package no.bekk.java.camelexamples;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class HttpExample extends CamelExampleMain {

    public static void main(String[] args) throws Exception {
        HttpExample m = new HttpExample();
        m.addRoute(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("timer:checkDigipostAvailability?period=5000")
                .to("https://www.digipost.no/post/logginn.html?throwExceptionOnFailure=false")
                .transform().simple("${in.headers.CamelHttpResponseCode}")
                .choice().when(simple("${body} != 200"))
                .log(LoggingLevel.WARN, "Digipost is not available");
            }
        });
        m.start();
    }
}
