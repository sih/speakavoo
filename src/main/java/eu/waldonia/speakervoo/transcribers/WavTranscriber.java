package eu.waldonia.speakervoo.transcribers;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sih
 */
@Service
public class WavTranscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(WavTranscriber.class);

    @Value("${transcriber.result.topN}")
    private int topn;

    @Value("${transcriber.acousticModelPath}")
    private String acousticModelPath;

    @Value("${transcriber.dictionaryPath}")
    private String dictionaryPath;

    @Value("${transcriber.languageModelPath}")
    private String languageModelPath;

    private Configuration configuration;

    /**
     * Set up the transcriber with its props
     */
    public void config() {

        configuration = new Configuration();

        configuration.setAcousticModelPath(acousticModelPath);
        configuration.setDictionaryPath(dictionaryPath);
        configuration.setLanguageModelPath(languageModelPath);
    }

    /**
     * @param fileLocation
     * @return
     * @throws IOException
     */
    public Map<Long,List<String>> transcribe(String fileLocation) throws IOException {

        long start = System.currentTimeMillis();

        Map<Long,List<String>> hypothesesByTimecode = new HashMap<>();

        if (null == configuration) config();

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);


        InputStream stream = new FileInputStream(new File(fileLocation));
        recognizer.startRecognition(stream);
        SpeechResult result;


        while ((result = recognizer.getResult()) != null) {
            LOGGER.info("Hypothesis: %s\n", result.getHypothesis());

            LOGGER.info("List of recognized words and their times:");
            for (WordResult r : result.getWords()) {
                LOGGER.info(r.toString());
            }


            long collectTime = result.getResult().getCollectTime();
            Collection<String> topNResults = result.getNbest(topn);

            LOGGER.info("Best 3 hypothesis:");
            for (String s : topNResults) {
                LOGGER.info(s);
            }

        }

        recognizer.stopRecognition();

        long elapsed = Math.round((System.currentTimeMillis() - start)/1000D);

        LOGGER.info("Transcription took "+elapsed+ "s");


        return hypothesesByTimecode;
    }


}
