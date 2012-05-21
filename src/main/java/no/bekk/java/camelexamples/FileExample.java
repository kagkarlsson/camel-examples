package no.bekk.java.camelexamples;

import org.apache.camel.builder.RouteBuilder;

public class FileExample extends CamelExampleMain {

    public static void main(String[] args) throws Exception {
        FileExample m = new FileExample();
        m.addRoute(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("file://testing/fromDir?filter=#myFileFilter")
                .to("file://testing/toDir");
            }
        });
        m.start();
    }
}
