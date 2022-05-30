package main.models;

public class Author {

    private String lastname;
    private String firstname;
    private boolean lastFirstOrder;

    public Author(String lastname) {
        this.lastname = lastname;
    }

    public Author(String lastname, String firstname, boolean lastFirstOrder) {
        this(lastname);
        this.firstname = firstname;
        this.lastFirstOrder = lastFirstOrder;
    }

    public String getLastname() {
        return lastname;
    }

    public String fullNameForComparing() {
        String name = getLastPartOfLastname();

        if (!name.equals(lastname)) {
            String firstPart = lastname.replace(name, "").replace(" ", "");
            name += " " + firstPart;
        }

        if (firstname != null) {
            name += " " + firstname;
        }

        return name;
    }

    private String getLastPartOfLastname() {
        if (lastname.contains(" ") && Character.isLowerCase(lastname.charAt(0))) {
            return lastname.substring( lastname.lastIndexOf(" ") + 1 );
        }

        return lastname;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Author author) {
            if (this.firstname == null || author.firstname == null) {
                return this.lastname.equals(author.lastname);
            }

            return this.lastname.equals(author.lastname)
                    && this.firstname.equals(author.firstname)
                    && this.lastFirstOrder == author.lastFirstOrder;
        }

        return false;
    }

    @Override
    public String toString() {
        if (firstname == null) {
            return lastname;
        }

        if (lastFirstOrder) {
            return lastname + " " + firstname;
        }

        return firstname + " " + lastname;
    }

}
