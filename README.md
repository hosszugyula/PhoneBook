# PhoneBook

I made this [maven project](https://maven.apache.org/) in Java language by a university assignment.
  - I created the graphical interface using OpenJFX.
  - I manage the data using remote-mysql.
  
The program is a phonebook application in which we can:
  - add 
  ```
  public void addContact(Persons persons) {
        logger.info("Contacts Add To Database");
        jdbi.installPlugin(new SqlObjectPlugin());
        List<Persons> personsadd = jdbi.withExtension(PersonsDao.class, dao -> {
            dao.addPersons(persons.getId(), persons.getLastName(), persons.getFirstName(), persons.getPhoneNumber());

            return dao.listpersons();

        });
    }
  ```
  - update
  ```
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
  ```
  - delete contacts
  ```
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
  ```
  - generate a pdf of contacts
  ```
   private void exportList(ActionEvent event) {
        logger.info("Pdf generated");
        String fileName = inputExportName.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            PdfGeneration pdfCreator = new PdfGeneration();
            pdfCreator.pdfGeneration(fileName, data);
        }else{
            alert("Adj meg egy fájlnevet!");
        }
    }
  ```
  I log the operation of the program by [Apache Log4j 2](https://logging.apache.org/log4j/2.x/).
  
 The tests were performed on [JUnit 5 programmer-friendly testing framework](https://junit.org/junit5/).
 
 The executable JAR file should be run as follows:
 ```
 java -jar ./target/PhoneBook-1.0-shaded.jar
 ```
