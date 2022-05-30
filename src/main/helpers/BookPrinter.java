package main.helpers;

import main.models.Book;
import main.services.DataHandler;

public class BookPrinter {

    private static BookPrinter printer;

    private DataHandler handler;

    private BookPrinter() {
        handler = DataHandler.getInstance();
    }

    public static BookPrinter getInstance() {
        if (printer == null) {
            printer = new BookPrinter();
        }

        return printer;
    }

    public void print() {
        String formedSerial = "%03d";
        int serial = 1;

        for (Book book : handler.getBooks()) {
            System.out.println(String.format(formedSerial, serial) + ".\t" + book);
            serial++;
        }
    }

    public void printWithAvg() {
        String formedSerial = "%03d";
        int serial = 1;

        int read = 0, marked = 0;

        for (Book book : handler.getBooks()) {
            if (book.isAlreadyRead()) {
                read++;
            } else if (book.isMarkedForRead()) {
                marked++;
            }

            System.out.println(String.format(formedSerial, serial) + ".\t" + book);
            serial++;
        }

        System.out.println("\n-----------------------\n");

        printAvg(handler.getBooks().size(), marked, read);
    }

    private void printAvg(double all, int marked, int read) {
        int left = (int) all - read - marked;

        System.out.printf("Összesen: %.0f%n", all);
        System.out.println("---------------");
        System.out.printf("Olvasott: %d - %.2f%%%n", read, countAvg(read, all));
        System.out.println("---------------");
        System.out.printf("Olvasásra jelölt: %d - %.2f%%%n", marked, countAvg(marked, all));
        System.out.println("---------------");
        System.out.printf("Maradt még: %d - %.2f%%%n", left, countAvg(left, all));
    }

    private double countAvg(int value, double all) {
        return value / all * 100;
    }

}
