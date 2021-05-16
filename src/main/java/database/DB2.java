package database;

import java.sql.*;
import java.util.List;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import person.Persons;
import person.PersonsDao;

/**
 * Class representing how the database works
 *
 * @author Gyula
 */
public class DB2 {
    Connection conn = null;
    String URL = "jdbc:mysql://remotemysql.com:3306/eTtWTwnkmb?useSSL=false";
    String DRIVER = "com.mysql.jdbc.Driver";
    String  USERNAME = "eTtWTwnkmb";
    String  PASSWORD = "Ae0kmBCpPY";

    Logger logger = LogManager.getLogger(getClass());

    Jdbi jdbi = Jdbi.create(URL,USERNAME,PASSWORD);

    /**
     * We are trying to make the connection with the database.
     */
    public DB2() {
        try {
            String DRIVER = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            logger.info("\n Connection READY");
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns the list of contacts in database
     *
     * @return the list of contacts in database
     *
     */
    public List<Persons> getAllContacts() {
        logger.info("Getting all Contacts");
        jdbi.installPlugin(new SqlObjectPlugin());
        List<Persons> persons = jdbi.withExtension(PersonsDao.class, dao -> {

          return dao.listpersons();
        });
        return persons;
    }

    /**
     * Returns the {@code persons} contact who's added to database.
     * @param persons a contact
     */
    public void addContact(Persons persons) {
        logger.info("Contacts Add To Database");
        jdbi.installPlugin(new SqlObjectPlugin());
        List<Persons> personsadd = jdbi.withExtension(PersonsDao.class, dao -> {
            dao.addPersons(persons.getId(), persons.getLastName(), persons.getFirstName(), persons.getPhoneNumber());

            return dao.listpersons();

        });
    }

    /**
     * Return a {@code presons} person who's information has been updated
     * @param persons a contact
     */
    public void updateContact(Persons persons) {
        jdbi.installPlugin(new SqlObjectPlugin());
        List<Persons> personsup = jdbi.withExtension(PersonsDao.class, dao -> {
            jdbi.useHandle(handle -> {
                handle.execute("UPDATE persons SET `id`= '"+persons.getId()+"', `firstName` = '"+persons.getFirstName()+"', `lastName`= '"+persons.getLastName()+"',`phoneNumber`= '"+persons.getPhoneNumber()+"' WHERE `id` = '"+persons.getId()+"'");

            });
            return dao.listpersons();
        });
        logger.info("The Contact being Updated");
    }

    /**
     * Remove {@code persons} a contact from the database
     * @param persons a contact
     */
    public void removeContact(Persons persons) {
        jdbi.installPlugin(new SqlObjectPlugin());
        List<Persons> personsrem = jdbi.withExtension(PersonsDao.class, dao -> {
            jdbi.useHandle(handle -> {
                handle.execute("DELETE FROM `persons` WHERE `id` = '"+persons.getId()+"'");

            });
            return dao.listpersons();
        });
        logger.info("Contacts Removed From Database");
    }
}
