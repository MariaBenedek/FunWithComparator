package main.models;

import main.TestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void bookCreation() {
        Book b = TestHelper.fullSetBook();

        assertEquals(new Author("Gesztenye"), b.getAuthor());
        assertEquals("Lent a parkban", b.getTitle());
        assertEquals((short) 2022, b.getReleased());
        assertEquals("avagy ahová a gesztenye is lehullik egyszer", b.getSubTitle());
        assertEquals("komment", b.getComment());
        assertTrue(b.isAlreadyRead());
        assertTrue(b.isMarkedForRead());
        assertTrue(b.isOwned());
    }

    @Test
    void toStringTest() {
        Book b = TestHelper.baseSetBook();
        assertEquals("Gesztenye - Lent a parkban (2022)", b.toString());

        b.setAlreadyRead(true);
        assertEquals("(olvasott) Gesztenye - Lent a parkban (2022)", b.toString());

        b.setOwned(true);
        assertEquals("(olvasott) [+] Gesztenye - Lent a parkban (2022)", b.toString());

        b.setMarkedForRead(true);
        assertEquals("(!) (olvasott) [+] Gesztenye - Lent a parkban (2022)", b.toString());

        b.setAlreadyRead(false);
        assertEquals("(!) [+] Gesztenye - Lent a parkban (2022)", b.toString());

        b.setMarkedForRead(false);
        b.setSubTitle("avagy az avarban");
        assertEquals("[+] Gesztenye - Lent a parkban (2022) - avagy az avarban", b.toString());

        b.setComment("komment");
        assertEquals("[+] Gesztenye - Lent a parkban (2022) - avagy az avarban /*komment*/", b.toString());
    }

}