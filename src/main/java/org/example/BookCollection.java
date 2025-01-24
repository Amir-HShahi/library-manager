package org.example;

import utility.BookExistException;
import utility.BookNotFoundException;
import utility.ISBNException;

import java.util.ArrayList;

public class BookCollection {
    public static ArrayList<Book> booksShelf = new ArrayList<>();


    public static void addBook(Book book) throws ISBNException, BookExistException {
        try {
            //check if book already added or not
            getBook(book.getIsbn10());
            getBook(book.getIsbn13());
            throw new BookExistException();
        } catch (BookNotFoundException e) {
            //try to add initial book if fields valid
            booksShelf.add(new Book(book.getTitle(), book.getAuthor(), book.getIsbn10(), book.getIsbn13()));
        }
    }

    public static Book getBook(String isbn) throws BookNotFoundException {
        for (Book book : booksShelf) {
            if ((isbn.equals(book.getIsbn13())) || (isbn.equals(book.getIsbn10()))) {
                return book;
            }
        }
        //if book not found
        throw new BookNotFoundException();
    }
}
