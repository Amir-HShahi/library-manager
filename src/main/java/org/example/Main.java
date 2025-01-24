package org.example;
import com.google.gson.Gson;
import utility.*;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        post("api/v1/addBook", (request, response) -> {
            //set response type to json
            response.type("application/json");
            //get the json body
            String jsonBody = request.body();
            //initial book
            Book book = new Gson().fromJson(jsonBody, Book.class);
            try {
                //try to add book if all info is valid
                BookCollection.addBook(book);
                response.type("201"); //created
                //return book means successful add book
                return new Gson().toJson(book);
            } catch (ISBNException e) {
                //invalid isbn
                response.type("422"); //unprocessable content
                //return message
                return new Gson().toJson("Invalid ISBN");
            } catch (BookExistException e) {
                //book added before
                response.type("422"); //unprocessable content
                return new Gson().toJson("Book already exist!");
            }
        });

        post("api/v1/borrowBook", (request, response) -> {
            //set response type to json
            response.type("application/json");
            //get isbn param
            String isbn = request.queryParams("isbn");
            try {
                //search for book
                Book book = BookCollection.getBook(isbn);
                //if book found
                book.borrowThis();
                response.type("201"); //request fulfilled
                //return message
                return new Gson().toJson(book.getTitle() + " borrowed!");
            } catch (BookNotFoundException e) {
                response.type("404"); //not found
                //return message
                return new Gson().toJson("Not found!");
            } catch (NoRemainingException e) {
                response.type("422"); //unprocessable
                //return message
                return new Gson().toJson("No more remaining of this book!");
            }

        });

        post("api/v1/returnBook", (request, response) -> {
            //set response type to json
            response.type("application/json");
            //get isbn param
            String isbn = request.queryParams("isbn");
            try {
                //search for book
                Book book = BookCollection.getBook(isbn);
                book.returnThis();
                response.type("201"); //request fulfilled
                //return message
                return new Gson().toJson(book.getTitle() + " returned!");
            } catch (BookNotFoundException e) {
                response.type("404"); //not found
                return new Gson().toJson("Not found!");
            } catch (NotBorrowedException e) {
                response.type("666");
                return new Gson().toJson("idiot it isn't borrowed to be returned :-/");
            }
        });

        get("api/v1/getBook", (request, response) -> {
            //set response type to json
            response.type("application/json");
            //get isbn param
            //if null means requested all of books
            String isbn = request.queryParams("isbn");
            if (isbn == null) {
                response.type("201"); //request fulfilled
                //return all books
                return new Gson().toJson(BookCollection.booksShelf);
            } else {
                //search for specific book with isbn
                try {
                    //search and return book
                    return new Gson().toJson(BookCollection.getBook(isbn));
                } catch (BookNotFoundException e) {
                    response.type("404"); //not found
                    return new Gson().toJson("Not found!");
                }
            }
        });



    }
}