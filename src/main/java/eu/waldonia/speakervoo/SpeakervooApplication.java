package eu.waldonia.speakervoo; /**
 * @author sih
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpeakervooApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpeakervooApplication.class)
                .web(true)
                .run(args);
    }

}
