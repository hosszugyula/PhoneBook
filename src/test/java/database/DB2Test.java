package database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import person.Persons;
import person.PersonsDao;

import static org.junit.jupiter.api.Assertions.*;

public class DB2Test {

    private Persons person1;
    private Persons person2;
    private Persons person3;
    private Persons person4;
    private Persons person5;
    private Persons person6;
    private PersonsDao personsDao;

    private DB2 db;

    @BeforeEach
    public void initialize() {
            person1 = new Persons("Kiss", "István", "06302345678");
            person2 = new Persons("Nagy", "István", "+36301112233");
            person3 = new Persons("Kiss", "László", "06202345678");
            person4 = new Persons("Lakatos", "Ábel", "06402345678");
            person5 = new Persons("Ácsi", "Milán", "06702345678");
            person6 = new Persons("Elek", "János", "06502345678");

            db = new DB2();
        }

    @Test
    public void testSizeOfContacts(){
        assertEquals(true, db.getAllContacts().size() != 0);
    }

    @Test
    public void getContactsWhatPhoneNumbersIs30() {
        assertEquals(true, person1.getPhoneNumber().contains("0630"));
        assertEquals(true, person2.getPhoneNumber().contains("+3630"));
        assertEquals(false, person3.getPhoneNumber().contains("0630"));
        assertEquals(false, person6.getPhoneNumber().contains("0630"));

    }

    @Test
    public void getContactsWhatPhoneNumbersIs40() {
        assertEquals(true, person4.getPhoneNumber().contains("0640"));
        assertEquals(false, person1.getPhoneNumber().contains("0640"));
        assertEquals(false, person2.getPhoneNumber().contains("+3640"));
        assertEquals(false, person3.getPhoneNumber().contains("0640"));


    }

    @Test
    public void addContact(){
        db.addContact(person1);
    }

    @Test
    public void updateContact(){
        db.updateContact(person1);
    }

    @Test
    public void removeContact(){
        db.removeContact(person1);
    }

    @Test
    public void givenLoggerWithDefaultConfig_whenLogToConsole_thanOK()
            throws Exception {
        Logger logger = LogManager.getLogger(getClass());
        Exception ex = new RuntimeException("This is only a test!");

        logger.info("This is a simple message at INFO level. " +
                "It will be hidden.");
        logger.error("This is a simple message at ERROR level. " +
                "This is the minimum visible level.", ex);
    }
}