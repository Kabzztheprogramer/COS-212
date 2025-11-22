public class Person implements Comparable<Person> {
    private String name;
    private String surname;

    public Person(String fullName) {
        this.name = fullName.split(" ")[0];
        this.surname = fullName.split(" ")[1];
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    @Override
    public int compareTo(Person o) {
        if (surname.equals(o.surname) && name.equals(o.name)) {
            return 0;
        }

        if (surname.equals(o.surname)) {
            if (name.compareTo(o.name) < 0) {
                return -1;
            } else {
                return 1;
            }
        }
        if (surname.compareTo(o.surname) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

}
