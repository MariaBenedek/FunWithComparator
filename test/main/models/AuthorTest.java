package main.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void fullNameForComparing_ifLastnameOnly() {
        Author author = new Author("Alma");
        assertEquals("Alma", author.fullNameForComparing());
    }

    @Test
    void fullNameForComparing_ifLastFirstOrder() {
        Author author = new Author("Alma", "Béla", true);
        assertEquals("Alma Béla", author.fullNameForComparing());
    }

    @Test
    void fullNameForComparing_ifFirstLastOrder() {
        Author author = new Author("Alma", "Béla", false);
        assertEquals("Alma Béla", author.fullNameForComparing());
    }

    @Test
    void fullNameForComparing_ifMoreLastnameUppercase() {
        Author author = new Author("Alma Körte", "Béla", true);
        assertEquals("Alma Körte Béla", author.fullNameForComparing());
    }

    @Test
    void fullNameForComparing_ifMoreLastnameLowercase() {
        Author author = new Author("alma Körte", "Béla", true);
        assertEquals("Körte alma Béla", author.fullNameForComparing());
    }

    @Test
    void toString_ifLastnameOnly() {
        Author author = new Author("Alma");
        assertEquals("Alma", author.toString());
    }

    @Test
    void toString_ifLastFirstOrder() {
        Author author = new Author("Alma", "Béla", true);
        assertEquals("Alma Béla", author.toString());
    }

    @Test
    void toString_ifFirstLastOrder() {
        Author author = new Author("Alma", "Béla", false);
        assertEquals("Béla Alma", author.toString());
    }

    @Test
    void equals_ifLastnameOnly() {
        Author a1 = new Author("Iksz");
        Author a2 = new Author("Iksz");
        Author a3 = new Author("Epszilon");

        assertEquals(a1, a2);
        assertNotEquals(a1, a3);
    }

    @Test
    void equals_ifAllFieldSet() {
        Author a1 = new Author("Iksz", "Béla", true);
        Author a2 = new Author("Iksz", "Béla", true);
        Author a3 = new Author("Iksz", "Miranda", true);
        Author a4 = new Author("Epszilon", "Béla", true);
        Author a5 = new Author("Iksz", "Béla", false);
        Author a6 = new Author("Mizse", "Lajos", false);

        assertEquals(a1, a2);
        assertNotEquals(a1, a3);
        assertNotEquals(a1, a4);
        assertNotEquals(a1, a5);
        assertNotEquals(a1, a6);
        assertNotEquals(a1, null);
    }

}