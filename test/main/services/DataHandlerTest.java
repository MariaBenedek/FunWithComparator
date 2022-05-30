package main.services;

import main.TestHelper;
import main.helpers.FileHelper;
import main.models.Author;
import main.models.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataHandlerTest {

    private static DataHandler handler;

    @BeforeAll
    static void setup() {
        handler = DataHandler.getInstance();
    }

    @Test
    void removeSign() {
        String methodName = "removeSign";

        Object nothingToRemove = TestHelper.invoke(methodName, handler, "kiscica", "*");
        assertEquals("kiscica", nothingToRemove.toString());

        Object starRemove = TestHelper.invoke(methodName, handler, "kiscica*", "*");
        assertEquals("kiscica", starRemove.toString());

        Object starRemoveButNotPlus = TestHelper.invoke(methodName, handler, "kiscica*+", "*");
        assertEquals("kiscica+", starRemoveButNotPlus.toString());

        Object moreToRemove = TestHelper.invoke(methodName, handler, "+ki+sci+ca+", "+");
        assertEquals("kiscica", moreToRemove.toString());
    }

    @Test
    void checkAndSetSigns_ifNothingToSet() {
        Book book = TestHelper.baseSetBook();
        String[] bookLine = {"Gesztenye", "Lent a parkban", "2022"};
        TestHelper.invoke("checkAndSetSigns", handler, book, bookLine);

        assertBookSet(book, false, false, false);
        assertArraySet(new String[]{"Gesztenye", "Lent a parkban", "2022"}, bookLine, "2022");
    }

    @Test
    void checkAndSetSigns_ifOwned() {
        Book book = TestHelper.baseSetBook();
        String[] bookLine = {"Gesztenye", "Lent a parkban", "2020+"};
        TestHelper.invoke("checkAndSetSigns", handler, book, bookLine);

        assertBookSet(book, true, false, false);
        assertArraySet(new String[]{"Gesztenye", "Lent a parkban", "2020+"}, bookLine, "2020");
    }

    @Test
    void checkAndSetSigns_ifOwnedAndRead() {
        Book book = TestHelper.baseSetBook();
        String[] bookLine = {"Gesztenye", "Lent a parkban", "2022*+"};
        TestHelper.invoke("checkAndSetSigns", handler, book, bookLine);

        assertBookSet(book, true, true, false);
        assertArraySet(new String[]{"Gesztenye", "Lent a parkban", "2022*+"}, bookLine, "2022");
    }

    @Test
    void checkAndSetSigns_ifOwnedAndMarked() {
        Book book = TestHelper.baseSetBook();
        String[] bookLine = {"Gesztenye", "Lent a parkban", "2022!+"};
        TestHelper.invoke("checkAndSetSigns", handler, book, bookLine);

        assertBookSet(book, true, false, true);
        assertArraySet(new String[]{"Gesztenye", "Lent a parkban", "2022!+"}, bookLine, "2022");
    }

    @Test
    void checkAndSetSigns_ifAllSign() {
        Book book = TestHelper.baseSetBook();
        String[] bookLine = {"Gesztenye", "Lent a parkban", "2022!*+"};
        TestHelper.invoke("checkAndSetSigns", handler, book, bookLine);

        assertBookSet(book, true, true, true);
        assertArraySet(new String[]{"Gesztenye", "Lent a parkban", "2022!*+"}, bookLine, "2022");
    }

    @Test
    void createBook_baseBook() {
        String[] bookLine = "Apuleius, Lucius; Az aranyszamár; 160".split(FileHelper.SPLIT_BOOK);
        Object o = TestHelper.invoke("createBook", handler, TestHelper.createAuthor(), bookLine);

        assertBookEquals((Book) o,
                "Az aranyszamár", (short) 160, false, false, false);
    }

    @Test
    void createBook_ownedAndReadBook() {
        String[] bookLine = "Tolkien, J. R. R.; A hobbit; 1937*+".split(FileHelper.SPLIT_BOOK);
        Object o = TestHelper.invoke("createBook", handler, TestHelper.createAuthor(), bookLine);

        assertBookEquals((Book) o,
                "A hobbit", (short) 1937, true, true, false);
    }

    @Test
    void createBook_markedWithSubtitle() {
        String[] bookLine = "Mikszáth Kálmán; Tót atyafiak; 1881; Elbeszélések és rajzok róluk!".split(FileHelper.SPLIT_BOOK);
        Object o = TestHelper.invoke("createBook", handler, TestHelper.createAuthor(), bookLine);

        assertBookEquals((Book) o,
                "Tót atyafiak", (short) 1881, false, false, true);
        assertEquals("Elbeszélések és rajzok róluk", ((Book) o).getSubTitle());
        assertNull(((Book) o).getComment());
    }

    @Test
    void createBook_withoutSubtitleWithComment() {
        String[] bookLine = "Marque; Szodoma százhúsz napja; 1785;; ez egy gusztustalan könyv".split(FileHelper.SPLIT_BOOK);
        Object o = TestHelper.invoke("createBook", handler, TestHelper.createAuthor(), bookLine);

        assertBookEquals((Book) o,
                "Szodoma százhúsz napja", (short) 1785, false, false, false);
        assertNull(((Book) o).getSubTitle());
        assertEquals("ez egy gusztustalan könyv", ((Book) o).getComment());
    }

    @Test
    void createBook_withSubtitleWithComment() {
        String[] bookLine = "Austen, Jane; Büszkeség és balítélet; 1813; alcím; I love Darcy*+".split(FileHelper.SPLIT_BOOK);
        Object o = TestHelper.invoke("createBook", handler, TestHelper.createAuthor(), bookLine);

        assertBookEquals((Book) o,
                "Büszkeség és balítélet", (short) 1813, true, true, false);

        assertEquals("alcím", ((Book) o).getSubTitle());
        assertEquals("I love Darcy", ((Book) o).getComment());
    }

    @Test
    void createAuthor_oneName() {
        String authorLine = "Stendhal";
        Object o = TestHelper.invoke("createAuthor", handler, authorLine);

        assertEquals(new Author("Stendhal"), o);
    }

    @Test
    void createAuthor_firstLastOrder() {
        String authorLine = "Tolsztoj, Lev";
        Object o = TestHelper.invoke("createAuthor", handler, authorLine);

        assertEquals(new Author("Tolsztoj", "Lev", false), o);
    }

    @Test
    void createAuthor_lastFirstOrder() {
        String authorLine = "Kosztolányi Dezső";
        Object o = TestHelper.invoke("createAuthor", handler, authorLine);

        assertEquals(new Author("Kosztolányi", "Dezső", true), o);
    }

    @Test
    void createAuthor_moreLast() {
        String authorLine = "de Saint-Exupéry, Antoine";
        Object o = TestHelper.invoke("createAuthor", handler, authorLine);

        assertEquals(new Author("de Saint-Exupéry", "Antoine", false), o);
    }

    @Test
    void findOrAddAuthor() {
        TestHelper.clearLists(handler);

        String methodName = "findOrAddAuthor";

        Author a1 = TestHelper.createAuthor();
        Author a2 = new Author("Mizse", "Lajos", true);
        Author a3 = new Author("Mizse", "Lajos", true);

        assertEquals(0, handler.getAuthors().size());

        Object invoke1 = TestHelper.invoke(methodName, handler, a1);
        assertEquals(1, handler.getAuthors().size());
        assertEquals(a1, invoke1);

        Object invoke2 = TestHelper.invoke(methodName, handler, a2);
        assertEquals(2, handler.getAuthors().size());
        assertEquals(a2, invoke2);

        Object invoke3 = TestHelper.invoke(methodName, handler, a3);
        assertEquals(2, handler.getAuthors().size());
        assertEquals(a2, invoke3);
    }

    @Test
    void readFile_ok() {
        TestHelper.clearLists(handler);

        assertFalse(exceptionInReadFile(TestHelper.VALID_PATH));
        assertEquals(10, handler.getBooks().size());
        assertEquals(5, handler.getAuthors().size());
    }

    @Test
    void readFile_notOk() {
        TestHelper.clearLists(handler);

        assertTrue(exceptionInReadFile(TestHelper.INVALID_PATH));
        assertEquals(0, handler.getBooks().size());
        assertEquals(0, handler.getAuthors().size());
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    private boolean exceptionInReadFile(String filepath) {
        try {
            handler.readFile(filepath);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private void assertBookEquals(Book actual, String title, short released, boolean owned, boolean read, boolean marked) {
        assertEquals(title, actual.getTitle());
        assertEquals(released, actual.getReleased());

        assertBookSet(actual, owned, read, marked);
    }

    private void assertBookSet(Book actual, boolean owned, boolean read, boolean marked) {
        assertEquals(owned, actual.isOwned());
        assertEquals(read, actual.isAlreadyRead());
        assertEquals(marked, actual.isMarkedForRead());
    }

    private void assertArraySet(String[] original, String[] actual, String changedTo) {
        for (int i = 0; i < original.length - 1; i++) {
            assertEquals(original[i], actual[i]);
        }

        assertEquals(changedTo, actual[actual.length - 1]);
    }

}