package main;

import main.helpers.BookPrinter;
import main.helpers.FileHelper;
import main.services.CompareBuilder;
import main.services.DataHandler;

public class Main {

    public static void main(String[] args) {
        DataHandler handler = DataHandler.getInstance();

        handler.readFile(FileHelper.PATH);

        handler.getBooks().sort(
                new CompareBuilder().markedOwnedNoneReadOrder().authorLastnameOrder().titleOrder().build()
        );

        BookPrinter.getInstance().printWithAvg();
    }

}
