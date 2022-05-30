package main.services;

import main.TestHelper;
import main.models.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompareBuilderTest {

    @Test
    void authorLastnameOrder() {
        List<Book> books = TestHelper.bookList();
        books.sort(new CompareBuilder().authorLastnameOrder().build());

        assertEquals("la Fruit", books.get(0).getAuthor().getLastname());
        assertEquals("Gesztenye", books.get(3).getAuthor().getLastname());
        assertEquals("Makó", books.get(books.size() - 1).getAuthor().getLastname());
    }

    @Test
    void titleOrder() {
        List<Book> books = TestHelper.bookList();
        books.sort(new CompareBuilder().titleOrder().build());

        assertEquals("Alma", books.get(0).getTitle());
        assertEquals("Banán", books.get(1).getTitle());
        assertEquals("Lepottyantam", books.get(5).getTitle());
        assertEquals("Tánc a kés élén", books.get(books.size() - 1).getTitle());
    }

    @Test
    void releasedOrder() {
        List<Book> books = TestHelper.bookList();
        books.sort(new CompareBuilder().releasedOrder().build());

        assertEquals("Konyha a köbön", books.get(0).getTitle());
        assertEquals("Karamella", books.get(1).getTitle());
        assertEquals("Tánc a kés élén", books.get(4).getTitle());
        assertEquals("Sülve-főve együtt", books.get(books.size() - 1).getTitle());
    }

    @Test
    void readFirstOrder() {
        List<Book> books = TestHelper.bookList();
        books.sort(new CompareBuilder().readOrder(true).build());

        assertTrue(books.get(0).isAlreadyRead());
        assertTrue(books.get(2).isAlreadyRead());
        assertFalse(books.get(3).isAlreadyRead());
        assertFalse(books.get(books.size() - 1).isAlreadyRead());
    }

    @Test
    void readLastOrder() {
        List<Book> books = TestHelper.bookList();
        books.sort(new CompareBuilder().readOrder(false).build());

        assertFalse(books.get(0).isAlreadyRead());
        assertFalse(books.get(6).isAlreadyRead());
        assertTrue(books.get(7).isAlreadyRead());
        assertTrue(books.get(books.size() - 1).isAlreadyRead());
    }

    @Test
    void markedOwnedNoneReadOrder() {
        List<Book> books = TestHelper.bookList();
        books.sort(new CompareBuilder().markedOwnedNoneReadOrder().build());

        assertTrue(books.get(0).isMarkedForRead() && books.get(0).isOwned());
        assertTrue(books.get(1).isMarkedForRead() && !books.get(1).isOwned());
        assertTrue(!books.get(4).isMarkedForRead() && books.get(4).isOwned());
        assertTrue(!books.get(6).isMarkedForRead() && !books.get(6).isOwned());
        assertTrue(books.get(7).isAlreadyRead() && books.get(7).isOwned());
        assertTrue(books.get(books.size() - 1).isAlreadyRead() && !books.get(books.size() - 1).isOwned());
    }

    @Test
    void authorAndTitleOrder() {
        List<Book> books = TestHelper.bookList();
        books.sort(new CompareBuilder().authorLastnameOrder().titleOrder().build());

        assertEquals("la Fruit", books.get(0).getAuthor().getLastname());
        assertEquals("Dinnye", books.get(0).getTitle());
        assertEquals("Karamella", books.get(1).getTitle());

        assertEquals("Gesztenye", books.get(3).getAuthor().getLastname());
        assertEquals("Alma", books.get(3).getTitle());
        assertEquals("Banán", books.get(4).getTitle());

        assertEquals("Makó", books.get(books.size() - 3).getAuthor().getLastname());
        assertEquals("Rétegek", books.get(books.size() - 3).getTitle());
        assertEquals("Sír a világ", books.get(books.size() - 2).getTitle());
    }

}