package com.vsegouin.mowitnow.services.file;

import com.vsegouin.mowitnow.services.file.exceptions.MowerInstructionFileException;
import com.vsegouin.mowitnow.services.language.LanguageManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * FileControl Test cases.
 * Created by v.segouin on 13/09/2016.
 */
public class FileControlTest {
    private FileControl fc;

    @BeforeMethod
    public void setUp() throws Exception {
        fc = new FileControl(new LanguageManager());
    }

    /**
     * Test if the file check mecanism is correct.
     *
     * @throws MowerInstructionFileException
     * @throws IOException
     */
    @Test
    public void testCheckFileIntegrityWithCorrect() throws MowerInstructionFileException, IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("instructions.txt");
        File file = new File(url.getPath());
        List<String> commands = fc.readFile(file.getAbsolutePath());
        boolean isFileOk = fc.checkFileIntegrity(commands);
        assertEquals(isFileOk, true);
    }

    @Test(expectedExceptions = MowerInstructionFileException.class)
    public void testCheckFileIntegrity_MalFormattedMap() throws MowerInstructionFileException, IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("instructionsMalformattedMap.txt");
        File file = new File(url.getPath());
        List<String> commands = fc.readFile(file.getAbsolutePath());
        boolean isFileOk = fc.checkFileIntegrity(commands);
        assertEquals(isFileOk, true);
    }

    @Test(expectedExceptions = MowerInstructionFileException.class)
    public void testCheckFileIntegrity_MalFormattedMower() throws MowerInstructionFileException, IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("instructionsMalformattedMower.txt");
        File file = new File(url.getPath());
        List<String> commands = fc.readFile(file.getAbsolutePath());
        boolean isFileOk = fc.checkFileIntegrity(commands);
        assertEquals(isFileOk, true);
    }

    /**
     * Test if the file check mecanism is correct when we provide a file containing error.
     *
     * @throws MowerInstructionFileException
     * @throws IOException
     */
    @Test(expectedExceptions = MowerInstructionFileException.class)
    public void testCheckFileIntegrityWithInCorrect() throws MowerInstructionFileException, IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("instructionsError.txt");
        File file = new File(url.getPath());
        List<String> commands = fc.readFile(file.getAbsolutePath());
        boolean isFileOk = fc.checkFileIntegrity(commands);
        assertEquals(isFileOk, false);
    }

}