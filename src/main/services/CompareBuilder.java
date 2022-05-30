package main.services;

import main.models.Book;

import java.util.Comparator;
import java.util.function.Function;

public class CompareBuilder {

    private Comparator<Book> comparator;

    private <U extends Comparable<? super U>> CompareBuilder addComparator(Function<Book, ? extends U> function) {
        if (comparator == null) {
            comparator = Comparator.comparing(function);
        } else {
            comparator = comparator.thenComparing(function);
        }

        return this;
    }

    public CompareBuilder readOrder(boolean readFirst) {
        if (readFirst) {
            return addComparator(readFirstCompare());
        }

        return addComparator(readLastCompare());
    }

    public CompareBuilder markedOwnedNoneReadOrder() {
        return addComparator(markedOwnedNoneReadCompare());
    }

    public CompareBuilder authorLastnameOrder() {
        return addComparator(authorCompare());
    }

    public CompareBuilder titleOrder() {
        return addComparator(titleCompare());
    }

    public CompareBuilder releasedOrder() {
        return addComparator(releasedCompare());
    }

    public Comparator<Book> build() {
        return comparator;
    }

    /////////////////////////////////////////////////////////////////////////////

    private Function<Book, Boolean> readFirstCompare() {
        return book -> !book.isAlreadyRead();
    }

    private Function<Book, Boolean> readLastCompare() {
        return Book::isAlreadyRead;
    }

    private Function<Book, Integer> markedOwnedNoneReadCompare() {
        return book -> {
            int val = book.isAlreadyRead() ? 8 : 0;

            val += book.isMarkedForRead() ? 0 : 4;
            val += book.isOwned() ? 0 : 2;

            return val;
        };
    }

    private Function<Book, String> authorCompare() {
        return book -> book.getAuthor().fullNameForComparing();
    }

    private Function<Book, String> titleCompare() {
        return Book::getTitle;
    }

    private Function<Book, Short> releasedCompare() {
        return Book::getReleased;
    }

}
