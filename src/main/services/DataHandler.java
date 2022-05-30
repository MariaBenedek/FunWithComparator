package main.services;

import main.helpers.FileHelper;
import main.models.Author;
import main.models.Book;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataHandler {

    private static DataHandler handler;

    private List<Author> authors;
    private List<Book> books;

    private DataHandler() {
        authors = new ArrayList<>();
        books = new ArrayList<>();
    }

    public static DataHandler getInstance() {
        if (handler == null) {
            handler = new DataHandler();
        }

        return handler;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void readFile(String filepath) {
        try {
            Scanner scanner = new Scanner(new File(filepath));

            while (scanner.hasNextLine()) {
                String[] bookLine = scanner.nextLine().split(FileHelper.SPLIT_BOOK);

                Author authorFromFile = createAuthor(bookLine[0].trim());
                Author author = findOrAddAuthor(authorFromFile);

                Book book = createBook(author, bookLine);

                books.add(book);
            }

        } catch (Exception e) {
            //e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private Author createAuthor(String authorLine) {
        boolean lastFirstOrder = false;

        String[] lastFirstname = authorLine.split(FileHelper.SPLIT_AUTHOR);

        if (lastFirstname.length == 1) {
            lastFirstname = authorLine.split(FileHelper.SPLIT_AUTHOR_ALT);
            lastFirstOrder = true;
        }

        if (lastFirstname.length == 1) {
            return new Author(lastFirstname[0].trim());
        }

        return new Author(lastFirstname[0].trim(), lastFirstname[1].trim(), lastFirstOrder);
    }

    private Author findOrAddAuthor(Author author) {
        for (int i = authors.size() - 1; i >= 0; i--) {
            if (authors.get(i).equals(author)) {
                return authors.get(i);
            }
        }

        authors.add(author);

        return author;
    }

    private Book createBook(Author author, String[] bookLine) {
        Book book = new Book();
        book.setAuthor(author);

        checkAndSetSigns(book, bookLine);

        book.setTitle(bookLine[1].trim());
        book.setReleased( Short.parseShort(bookLine[2].trim()) );

        if (bookLine.length >= 4) {
            String subTitle = bookLine[3];
            if (!subTitle.isBlank()) {
                book.setSubTitle(subTitle.trim());
            }
        }

        if (bookLine.length >= 5) {
            book.setComment(bookLine[4].trim());
        }

        return book;
    }

    private void checkAndSetSigns(Book book, String[] bookLine) {
        String lastElem = bookLine[bookLine.length - 1];

        if (lastElem.endsWith(FileHelper.OWNED_SIGN)) {
            book.setOwned(true);
            lastElem = removeSign(lastElem, FileHelper.OWNED_SIGN);
        }

        if (lastElem.endsWith(FileHelper.READ_SIGN)) {
            book.setAlreadyRead(true);
            lastElem = removeSign(lastElem, FileHelper.READ_SIGN);
        }

        if (lastElem.endsWith(FileHelper.FOR_READ_SIGN)) {
            book.setMarkedForRead(true);
            lastElem = removeSign(lastElem, FileHelper.FOR_READ_SIGN);
        }

        bookLine[bookLine.length - 1] = lastElem;
    }

    private String removeSign(String text, String sign) {
        return text.replace(sign, "");
    }

}
