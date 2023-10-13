package ru.annikonenkov.day.first.person;


/**
 * Описание класса человека. Первый урок.
 * TODO: Домашка: добавить бабушек и дедушек.
 */
public class Person {

    public static void main(String args[]) {
        Person olgaGrandMa = new Person("Olga", "Ivanova", 65, null, null);
        Person kirillGrandPa = new Person("Kirill", "Petrov", 66, null, null);
        Person ninaMother = new Person("Нина", "Иванова", 45, olgaGrandMa, kirillGrandPa);

        Person marinaGrandMa = new Person("Marina", "Ivanova", 67, null, null);
        Person viktorGrandPa = new Person("Viktor", "Petrov", 69, null, null);
        Person sergeyFather = new Person("Сергей", "Иванов", 46, marinaGrandMa, viktorGrandPa);

        Person ivan = new Person("Ivan", "Ivanov", 12, ninaMother, sergeyFather);
        System.out.println(ivan);
    }

    private String name;
    private String lastname;
    private int age;
    private Person mother;
    private Person father;

    public Person(String aName, String aLastname, int aAge, Person aMother, Person aFather) {
        name = aName;
        lastname = aLastname;
        age = aAge;
        mother = aMother;
        father = aFather;
    }

    public Person getMother() {
        return mother;
    }

    public Person getFather() {
        return father;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInfoAboutMother() {
        return null;
    }

    public String toString() {//TODO: Переделать. Иначе вывод получается не очень удачным.
        String info = "\n Меня зовут ::: " + name + "; Моя фамилия ::: " + lastname + "; Мой возраст ::: " + age;
        if (mother != null) {
            info += "\n Мою маму зовут ::: " + mother;
        }
        if (this.father != null) {
            info += "\n Моего папу зовут ::: " + father;
        }
        return info;
    }

}
