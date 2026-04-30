
package com.emma.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import com.emma.models.Book;
import com.emma.repository.bookRepository;

public class mainController {

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfAuthor;

    @FXML
    private TextField tfYear;

    @FXML
    private TextField tfPages;

    @FXML
    private TableView<Book> tbBooks;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, Integer> colYear;

    @FXML
    private TableColumn<Book, Integer> colPages;

    @FXML
    private void handleBtnAction(javafx.event.ActionEvent event) throws Exception {

        Object source = event.getSource();

        if (source.toString().contains("Insertar")) {
            insertBook();
            loadBooks();
        } else if (source.toString().contains("Actualizar")) {
            // updateBook();
        } else if (source.toString().contains("Eliminar")) {
            // deleteBook();
        }
    }

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    private bookRepository repository = new bookRepository();

    @FXML
    private void initialize() {
        // Digamos que es un constructor
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<>("pages"));

        tbBooks.setItems(bookList);

    }

    private void insertBook() throws Exception {
        Book book = new Book(
                // Integer.parseInt(tfId.getText()),
                tfTitle.getText(),
                tfAuthor.getText(),
                Integer.parseInt(tfYear.getText()),
                Integer.parseInt(tfPages.getText()));

        bookList.add(book);
        repository.insert(book);
        clearFields();
    }

    private void clearFields() {
        tfId.clear();
        tfTitle.clear();
        tfAuthor.clear();
        tfYear.clear();
        tfPages.clear();
    }

    private void loadBooks() {
        try {
            bookList.clear();
            bookList.addAll(repository.getBooks());
            tbBooks.setItems(bookList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
