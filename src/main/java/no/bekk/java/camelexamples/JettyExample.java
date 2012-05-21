package no.bekk.java.camelexamples;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.joda.time.DateTime;

public class JettyExample extends CamelExampleMain {

    public static void main(String[] args) throws Exception {
        JettyExample m = new JettyExample();
        m.addRoute(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("jetty:http://localhost:8181/whatIsTheTime")
                .process(new WhatIsTheTimeHandler())
                .setHeader("CamelFileName", constant("current-time"))
                .to("file:testing/requestTime");
            }
        });
        m.start();
    }

    private static class WhatIsTheTimeHandler implements Processor {

        public void process(Exchange exchange) throws Exception {
            exchange.getOut().setBody(new DateTime().toString());
        }
        
    }
}
