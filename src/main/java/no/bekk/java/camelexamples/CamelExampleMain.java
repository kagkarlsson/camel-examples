package no.bekk.java.camelexamples;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spring.spi.ApplicationContextRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelExampleMain {

    private static final Logger LOG = LoggerFactory.getLogger(CamelExampleMain.class);
    protected List<RouteBuilder> routes = new ArrayList<RouteBuilder>();

    public void addRoute(RouteBuilder route) {
        routes.add(route);
    }

    public void start() throws Exception {
        ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        final CamelContext context = new DefaultCamelContext(new ApplicationContextRegistry(springContext));

        for (RouteBuilder r : routes) {
            context.addRoutes(r);
        }

        Runtime.getRuntime().addShutdownHook(new GracefulShutdown(springContext, context));

        springContext.start();
        context.start();

        awaitTermination();
    }

    private static void awaitTermination() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private static class GracefulShutdown extends Thread {
        private final AbstractApplicationContext springContext;
        private final CamelContext context;

        public GracefulShutdown(AbstractApplicationContext springContext, CamelContext context) {
            this.springContext = springContext;
            this.context = context;
        }

        @Override
        public void run() {
            LOG.info("Shutting down hub application");
            try {
                context.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            springContext.stop();
        }
    }

}
