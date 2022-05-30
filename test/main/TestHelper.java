package main;

import main.models.Author;
import main.models.Book;
import main.services.DataHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TestHelper {

    public static final String INVALID_PATH = "files/b.txt";
    public static final String VALID_PATH = "test/test_books.txt";

    public static void clearLists(DataHandler handler) {
        if (handler.getAuthors().size() > 0) {
            handler.getAuthors().clear();
        }

        if (handler.getBooks().size() > 0) {
            handler.getBooks().clear();
        }
    }

    public static List<Book> bookList() {
        Author a1 = new Author("Gesztenye");
        Author a2 = new Author("la Fruit", "Sweet", false);
        Author a3 = new Author("Makó", "Hagyma", true);

        Book[] books = {
                new Book(a3, "Tánc a kés élén", (short) 2011, null, null,
                        false, false, true),
                new Book(a1, "Alma", (short) 2018, null, null,
                        true, false, true),
                new Book(a1, "Banán", (short) 2019, null, null,
                        true, false, false),
                new Book(a2, "Dinnye", (short) 1999, null, null,
                        false, true, false),
                new Book(a2, "Konyha a köbön", (short) 1997, null, null,
                        false, true, true),
                new Book(a2, "Karamella", (short) 1998, null, null,
                        false, false, false),
                new Book(a3, "Sír a világ", (short) 2000, null, null,
                        false, true, false),
                new Book(a3, "Rétegek", (short) 2020, null, null,
                        false, false, true),
                new Book(a1, "Lepottyantam", (short) 2021, null, null,
                        true, false, false),
                new Book(a1, "Sülve-főve együtt", (short) 2022, null, null,
                        false, false, false)
        };

        return Arrays.asList(books);
    }

    public static Book fullSetBook() {
        Book b = new Book();

        b.setAuthor(createAuthor());
        b.setTitle("Lent a parkban");
        b.setReleased((short) 2022);
        b.setSubTitle("avagy ahová a gesztenye is lehullik egyszer");
        b.setComment("komment");
        b.setAlreadyRead(true);
        b.setMarkedForRead(true);
        b.setOwned(true);

        return b;
    }

    public static Book baseSetBook() {
        Book b = new Book();

        b.setAuthor(createAuthor());
        b.setTitle("Lent a parkban");
        b.setReleased((short) 2022);

        return b;
    }

    public static Author createAuthor() {
        return new Author("Gesztenye");
    }

    public static Object invoke(String methodName, Object o, Object... args) {
        Method method = findMethod(methodName, o);

        try {
            method.setAccessible(true);

            return method.invoke(o, args);
        } catch (NullPointerException e) {
            throw new RuntimeException("No method found with name " + methodName);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Cannot invoke method.");
        }
    }

    private static Method findMethod(String methodName, Object o) {
        for (Method method : o.getClass().getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }

        return null;
    }

}
