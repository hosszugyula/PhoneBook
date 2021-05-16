package controller;

import database.DB2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pdfgen.PdfGeneration;
import person.Persons;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class representing how the application works
 *
 * @author Gyula
 */

public class ViewController implements Initializable {

    @FXML
    private TableView table;
    @FXML
    private TextField inputLastname;
    @FXML
    private TextField inputFirstName;
    @FXML
    private TextField inputPhoneNumber;
    @FXML
    Button addNewContactButton;
    @FXML
    private StackPane menuPane;
    @FXML
    private Pane contactPane;
    @FXML
    private Pane exportPane;
    @FXML
    private SplitPane mainSplit;
    @FXML
    private AnchorPane anchor;
    @FXML
    private TextField inputExportName;
    @FXML
    Button exportButton;

    DB2 db = new DB2();
    Logger logger = LogManager.getLogger(getClass());


    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";

    private final ObservableList<Persons> data = FXCollections.observableArrayList();

    @FXML
    private void addContact(ActionEvent event) {
        Persons newPerson = new Persons(inputLastname.getText(), inputFirstName.getText(), inputPhoneNumber.getText());
        data.add(newPerson);
        db.addContact(newPerson);
        inputLastname.clear();
        inputFirstName.clear();
        inputPhoneNumber.clear();
    }

    @FXML
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

    public void setTableData() {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(130);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Persons, String>("lastName"));

        lastNameCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Persons, String>>) t -> {
                    Persons actualPerson = (Persons) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualPerson.setLastName(t.getNewValue());
                    db.updateContact(actualPerson);
                }
        );

        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(130);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Persons, String>("firstName"));

        firstNameCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Persons, String>>) t -> {
                    Persons actualPerson = (Persons) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualPerson.setFirstName(t.getNewValue());
                    db.updateContact(actualPerson);
                }
        );

        TableColumn phoneNumberCol = new TableColumn("Telefonszám");
        phoneNumberCol.setMinWidth(220);
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Persons, String>("phoneNumber"));
        phoneNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());

        phoneNumberCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<Persons, String>>) t -> {
                    Persons actualPerson = (Persons) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    actualPerson.setPhoneNumber(t.getNewValue());
                    db.updateContact(actualPerson);
                }
        );

        TableColumn removeCol = new TableColumn( "Törlés" );
        removeCol.setMinWidth(100);

        Callback<TableColumn<Persons, String>, TableCell<Persons, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<Persons, String> param) {
                        final TableCell<Persons, String> cell = new TableCell<>() {
                            final Button btn = new Button("Törlés");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction((ActionEvent event) ->
                                    {
                                        Persons persons = getTableView().getItems().get(getIndex());
                                        data.remove(persons);
                                        db.removeContact(persons);
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );
        
        table.getColumns().addAll(lastNameCol, firstNameCol, phoneNumberCol,removeCol);

        data.addAll(db.getAllContacts());

        table.setItems(data);
    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);

        nodeItemA.setExpanded(true);

        Node contactsNode = new ImageView(new Image(getClass().getResourceAsStream("/pictures/contacts.png")));
        Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/pictures/export.png")));
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST, contactsNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);

        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS -> selectedItem.setExpanded(true);
                        case MENU_LIST -> {
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                        }
                        case MENU_EXPORT -> {
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                        }
                        case MENU_EXIT -> {
                            logger.info("Disconnect");
                            System.exit(0);
                        }
                    }
                }

            }
        });

    }

    private void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();
    }

}
