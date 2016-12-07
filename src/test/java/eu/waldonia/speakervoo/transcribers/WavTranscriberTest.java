package eu.waldonia.speakervoo.transcribers;

import eu.waldonia.speakervoo.SpeakervooApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * @author sih
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpeakervooApplication.class)
public class WavTranscriberTest {

    @Value("${transcriberTest.rainSpainFile}")
    private String testFile1;

    @Autowired
    private WavTranscriber transcriber;

    @Before
    public void setUp() {
        transcriber.config();
    }

    @Test
    public void transcribeShouldReturnResultsFromTestFile() throws IOException{

        Map<Long,List<String>> results = transcriber.transcribe(testFile1);
        assertNotNull(results);
    }

}