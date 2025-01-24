package org.example;

import utility.ISBNException;
import utility.NoRemainingException;
import utility.NotBorrowedException;

public class Book {
    private String title;
    private String author;
    private String isbn10;
    private String isbn13;
    private boolean borrowed = false;

    public Book(String title, String auther, String isbn10, String isbn13) throws ISBNException {
        setTitle(title);
        setAuthor(auther);
        setIsbn10(isbn10);
        setIsbn13(isbn13);
    }

    public void borrowThis() throws NoRemainingException {
        checkRemaining();
        borrowed = true;
    }

    public void checkRemaining() throws NoRemainingException {
        if (borrowed) {
            throw new NoRemainingException();
        }
    }

    public void returnThis() throws NotBorrowedException {
        if (!borrowed) {
            throw new NotBorrowedException();
        }
        borrowed = false;
    }

    private boolean isISBN13(String isbn) {
        if (isbn.length() != 13) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            //convert char to int
            int digit = Character.getNumericValue(isbn.charAt(i));
            if (i % 2 == 0) {
                sum += digit;
            } else {
                sum += 3 * digit;
            }
        }
        //convert char to int
        int lastDigit = Character.getNumericValue(isbn.charAt(12));
        int checkLastDigit = 10 - sum % 10;
        if (checkLastDigit == 10) {
            checkLastDigit = 0;
        }

        return checkLastDigit == lastDigit;
    }

    private boolean isISBN10(String isbn) {
        if (isbn.length() != 10) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            //convert char to int
            sum += (10 - i) * (Character.getNumericValue(isbn.charAt(i)));
        }
        int lastDigit;
        //check "X" in isbn 10
        if (isbn.toLowerCase().charAt(9) == 'x') {
            lastDigit = 10;
        } else {
            //convert char to int
            lastDigit = Character.getNumericValue(isbn.charAt(9));
        }

        int checkLastDigit = 11 - (sum % 11);
        if (checkLastDigit == 11) {
            checkLastDigit = 0;
        }
        return checkLastDigit == lastDigit;
    }


    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn10(String isbn10) throws ISBNException {
        if (isISBN10(isbn10)) {
            this.isbn10 = isbn10;
        } else {
            throw new ISBNException();
        }
    }

    public void setIsbn13(String isbn13) throws ISBNException {
        if (isISBN13(isbn13)) {
            this.isbn13 = isbn13;
        } else {
            throw new ISBNException();
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", borrowed=" + borrowed +
                '}';
    }
}
