package search;

public class Person {

    private String name;
    private String surname;
    private String email;
    private String[] personArray;

    public Person(String[] information) {
        this.name = information[0];
        this.surname = information[1];
        if(information.length == 3){
            this.email = information[2];
        }
        this.personArray = information;
    }
    public String[] getPersonArray() {
        return personArray;
    }

    public String getFullText() {

        return String.format("%s %s%s",this.name,this.surname,this.email != null ? String.format(" %s",this.email) : "");
    }


}
