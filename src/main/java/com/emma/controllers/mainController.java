
package com.emma.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;

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
            if (!validate()) {
                return;
            } else {
                insertBook();
                loadBooks();
            }

        } else if (source.toString().contains("Actualizar")) {
            if (!validate()) {
                return;
            } else {
                updateBook();
                loadBooks();
            }

        } else if (source.toString().contains("Eliminar")) {
            deleteBook();
            loadBooks();
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
        loadBooks();

        tbBooks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectedBook) -> {
            if (selectedBook != null) {
                tfId.setText(String.valueOf(selectedBook.getId()));
                tfTitle.setText(selectedBook.getTitle());
                tfAuthor.setText(selectedBook.getAuthor());
                tfYear.setText(String.valueOf(selectedBook.getYear()));
                tfPages.setText(String.valueOf(selectedBook.getPages()));
            }
        });

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

    private void updateBook() throws Exception {
        Book book = new Book(
                Integer.parseInt(tfId.getText()),
                tfTitle.getText(),
                tfAuthor.getText(),
                Integer.parseInt(tfYear.getText()),
                Integer.parseInt(tfPages.getText()));

        // bookList.add(book);
        repository.update(book);
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

    private void deleteBook() throws Exception {
        int id = Integer.parseInt(tfId.getText());
        repository.delete(id);
        clearFields();
    }


    private boolean validate() {
        if (tfTitle.getText().trim().isEmpty() ||
                tfAuthor.getText().trim().isEmpty() ||
                tfYear.getText().trim().isEmpty() ||
                tfPages.getText().trim().isEmpty()) {
            showAlert("Campos incompletos", "Todos los campos son obligatorios.");
            return false;
        }

        try {
            int year = Integer.parseInt(tfYear.getText().trim());
            if (year > 2026 || year < 0) {
                showAlert("Año inválido", "El año no puede ser mayor a 2026 ni menor a 0");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Año inválido", "El año debe ser un número entero (ej. 2023).");
            return false;
        }

        try {
            int pages = Integer.parseInt(tfPages.getText().trim());
            if (pages <= 0) {
                showAlert("Páginas inválidas", "El número de páginas debe ser mayor a 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Páginas inválidas", "Las páginas deben ser un número entero (ej. 320).");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alertita = new Alert(Alert.AlertType.INFORMATION);

        alertita.setTitle(title);
        alertita.setHeaderText(null);
        alertita.setContentText(message);
        alertita.showAndWait();
    }

}
