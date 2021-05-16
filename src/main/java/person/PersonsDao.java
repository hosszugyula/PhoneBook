package person;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

/**
 * DAO class for the {@link Persons} entity.
 */
@RegisterBeanMapper(Persons.class)
public interface PersonsDao {

    /**
     *Returns the {@code id,firstName,lastName,phoneNumber} contact who's added to database.
     * @param id id of contact
     * @param firstName firstname of contact
     * @param lastName lastname of contact
     * @param phoneNumber phone number of contact
     * @return the contact datas what's added to database.
     */
    @SqlUpdate("INSERT INTO `persons`(`id`, `firstName`, `lastName`, `phoneNumber`) VALUES (?,?,?,?)")
    @GetGeneratedKeys
    long addPersons(@Bind("id")Integer id, @Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("phoneNumber") String phoneNumber);

    /**
     *Returns the {@code persons} contact who's added to database.
     * @param persons the contact
     * @return the contact who's added to database
     */
    @SqlUpdate("INSERT INTO `persons`(`id`, `firstName`, `lastName`, `phoneNumber`) VALUES (?,?,?,?)")
    @GetGeneratedKeys
    long addPersons(@BindBean Persons persons);

    /**
     *
     * @return list of persons
     */
    @SqlQuery("SELECT * FROM `persons`")
    @RegisterBeanMapper(Persons.class)
    List<Persons> listpersons();

}
