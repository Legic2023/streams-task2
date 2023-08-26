import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // Количество несовершеннолетних (т.е. людей младше 18 лет).
        long tCount = persons.stream()
                .filter(x -> x.getAge() < 18)
                .count();
        System.out.printf("1. В Лондоне бродят %s несовершеннолетних.%n", tCount);

        //Список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        List<String> recruitFamily = persons.stream()
                .filter(x -> (x.getAge() >= 18) && (x.getAge() <= 27))
                .filter(x -> (x.getSex() == Sex.MAN))
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.printf("2. В Лондоне можно мобилизовать %s призывников (первые три фамилии):%n", recruitFamily.size());
        recruitFamily.stream()
                .limit(3)
                .forEach(System.out::println);

        // Отсортированный по фамилии список людей с в/о в выборке (т.е. людей с в/о от 18 до 60 лет для женщин и до 65 лет для мужчин).
        List<Person> workingPeople = persons.stream()
                .filter(x -> x.getAge() >= 18)
                .filter(x -> x.getEducation() == Education.HIGHER)
                .filter(x -> x.getAge() <= 65)
                .filter(x -> (x.getAge() <= 60) || (x.getSex() == Sex.MAN))
                .sorted(Comparator.comparing(x -> x.getFamily()))
                .collect(Collectors.toList());
        System.out.printf("3. В Лондоне %s людей рабочего возраста (первые три человека):%n", workingPeople.size());
        workingPeople.stream()
                .limit(3)
                .forEach(System.out::println);

    }
}
