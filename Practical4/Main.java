public class Main {
        // Output for this Main is in output.txt
        public static void main(String[] args) {
                ClassList l = new ClassList();
                l.addPerson("Anna Bosman");
                l.addPerson("Werner Haugher");
                l.addPerson("Sean Macmillan");
                l.addPerson("Tinashe Austin");
                l.addPerson("Lucas Blanc");
                l.addPerson("Caelen Hill");
                l.addPerson("Reece Jordaan");
                l.addPerson("Aidan McKenzie");
                l.addPerson("Stefan Muller");
                l.addPerson("James Neale");
                System.out.println(l);
                System.out.println(l.findPerson("James Neale"));
                l.removePerson("Anna Bosman");
                l.removePerson("Werner Haugher");
                l.removePerson("Sean Macmillan");
                l.removePerson("Tinashe Austin");
                System.out.println(l);
        }
}