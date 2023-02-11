package org.emented;

import org.emented.configuration.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Application application = applicationContext.getBean("application", Application.class);

        application.run(args);
    }
}
