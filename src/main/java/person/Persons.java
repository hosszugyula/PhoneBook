package person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing the Contacts.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Persons {

    /**
     * The id of the contact.
     */
    public Integer id;
    /**
     * The firstname of the contact.
     */
    private String firstName;
    /**
     * The lastname of the contact.
     */
    private String lastName;
    /**
     * The phonenumber of the contact.
     */
    private String phoneNumber;

    /**
     *
     * @param firstName firstname of contact
     * @param lastName lastname of contact
     * @param phoneNumber phone number of contact
     */
    public Persons(String firstName, String lastName, String phoneNumber) {
        this(null, firstName, lastName, phoneNumber);
    }

}


