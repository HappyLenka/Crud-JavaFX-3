package com.emma.repository;

import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;

import com.emma.models.Book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class bookRepository {
    
public ObservableList<Book> getBooks() throws Exception {

    ObservableList<Book> lista = FXCollections.observableArrayList();

    Connection conn = DBConnection.getConnection();

    String sql = "SELECT * FROM books";
    PreparedStatement ps = conn.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {

        Book b = new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("year"),
                rs.getInt("pages")
        );

        lista.add(b);
    }

    return lista;
}

public void insert(Book b) throws Exception {
    Connection conn = DBConnection.getConnection();
    String sql = "INSERT INTO books (title, author, year, pages) VALUES ( ?, ?, ?, ?)";
    PreparedStatement ps = conn.prepareStatement(sql);

    //ps.setInt(1, b.getId());
    ps.setString(1, b.getTitle());
    ps.setString(2, b.getAuthor());
    ps.setInt(3, b.getYear());
    ps.setInt(4, b.getPages());

    ps.executeUpdate();
}

}