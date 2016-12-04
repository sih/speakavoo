import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author sih
 */
public class WavTranscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(WavTranscriber.class);

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

//         configuration.setSampleRate(44100);

        configuration
                .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration
                .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration
                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File("./src/test/resources/rain-spain-sid.wav"));
        // InputStream stream = new FileInputStream(new File("./src/test/resources/hutchinson-blurb-sid.wav"));
        recognizer.startRecognition(stream);
        SpeechResult result;


        while ((result = recognizer.getResult()) != null) {
            LOGGER.info("Hypothesis: %s\n", result.getHypothesis());

            LOGGER.info("List of recognized words and their times:");
            for (WordResult r : result.getWords()) {
                LOGGER.info(r.toString());
            }

            LOGGER.info("Best 3 hypothesis:");
            for (String s : result.getNbest(3))
                LOGGER.info(s);
        }
        recognizer.stopRecognition();
    }

}
