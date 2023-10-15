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
        //System.out.println(ivan.getInfoAboutParent());

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

    public String getInfoAboutParent() {
        String info = "";
        if (mother != null || father != null) {
            info += "\n Информация о родителях " + "'" + name + "'";
        }
        if (mother != null) {
            info += "\n" + name + ": Информация о маме ---> " + mother;
        } else {
            //info += "\n Матери нет.";
        }
        if (this.father != null) {
            info += "\n" + name + ": Информация о папе ---> " + father;
        } else {
            //info += "\n Отца нет.";
        }
        return info;
    }

    public String toString() {//TODO: Переделать. Иначе вывод получается не очень удачным.
        String info = " Имя ::: " + name + "; Фамилия ::: " + lastname + "; Возраст ::: " + age;
        info += getInfoAboutParent(); //TODO: При таком раскладе(рекурсия): выводится сначала "Мама" и вся родословная "Мамы", а потом "Папа" и вся родословная "Папы" - не очень удобно читать
        return info;
    }


    /**
     *  Имя ::: Ivan; Фамилия ::: Ivanov; Возраст ::: 12
     *  Информация о родителях 'Ivan'
     *  Информация о маме  --->  Имя ::: Нина; Фамилия ::: Иванова; Возраст ::: 45
     *  Информация о родителях 'Нина'
     *  Информация о маме  --->  Имя ::: Olga; Фамилия ::: Ivanova; Возраст ::: 65
     *  Информация о папе --->  Имя ::: Kirill; Фамилия ::: Petrov; Возраст ::: 66
     *  Информация о папе --->  Имя ::: Сергей; Фамилия ::: Иванов; Возраст ::: 46 <<<<---- - При таком отображении уходет
     *  Информация о родителях 'Сергей'
     *  Информация о маме  --->  Имя ::: Marina; Фамилия ::: Ivanova; Возраст ::: 67
     *  Информация о папе --->  Имя ::: Viktor; Фамилия ::: Petrov; Возраст ::: 69
     */
}
