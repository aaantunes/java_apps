package ca.jrvs.apps.twitter.Spring;

import ca.jrvs.apps.twitter.TwitterCLIRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;

//@SpringBootApplication(scanBasePackages = "ca.jrvs.apps.twitter")
public class TwitterCLISpringBoot implements CommandLineRunner {

    private TwitterCLIRunner runner;

    @Autowired
    public TwitterCLISpringBoot(TwitterCLIRunner runner) {
        this.runner = runner;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TwitterCLISpringBoot.class);

        //Turn off web
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String[] args) throws Exception {
        runner.run(args);
    }
}
