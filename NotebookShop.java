import java.util.*;

class Laptop {
    private String brand;
    private int ram;
    private int hdd;
    private String os;
    private String color;

    public Laptop(String brand, int ram, int hdd, String os, String color) {
        this.brand = brand;
        this.ram = ram;
        this.hdd = hdd;
        this.os = os;
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public int getRam() {
        return ram;
    }

    public int getHdd() {
        return hdd;
    }

    public String getOs() {
        return os;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Ноутбук{" +
                "бренд='" + brand + '\'' +
                ", оперативная память=" + ram +
                ", объем жесткого диска=" + hdd +
                ", операционная система='" + os + '\'' +
                ", цвет='" + color + '\'' +
                '}';
    }
}

public class NotebookShop {
    private Map<String, Set<Laptop>> laptopCategories = new HashMap<>();

    public void addLaptop(String category, Laptop laptop) {
        laptopCategories.computeIfAbsent(category, k -> new HashSet<>()).add(laptop);
    }

    public void filterLaptops(Map<String, Object> filters) {
        List<Laptop> filteredLaptops = new ArrayList<>();

        for (Set<Laptop> laptops : laptopCategories.values()) {
            for (Laptop laptop : laptops) {
                boolean passFilter = true;
                for (Map.Entry<String, Object> entry : filters.entrySet()) {
                    String criteria = entry.getKey();
                    Object value = entry.getValue();

                    switch (criteria) {
                        case "brand":
                            if (!laptop.getBrand().equalsIgnoreCase((String) value)) {
                                passFilter = false;
                            }
                            break;
                        case "ram":
                            if (laptop.getRam() < (int) value) {
                                passFilter = false;
                            }
                            break;
                        case "hdd":
                            if (laptop.getHdd() < (int) value) {
                                passFilter = false;
                            }
                            break;
                        case "os":
                            if (!laptop.getOs().equalsIgnoreCase((String) value)) {
                                passFilter = false;
                            }
                            break;
                        case "color":
                            if (!laptop.getColor().equalsIgnoreCase((String) value)) {
                                passFilter = false;
                            }
                            break;
                        default:
                            System.out.println("Неизвестный критерий");
                            return; // выходим из метода, чтобы не фильтровать ноутбуки
                    }
                }
                if (passFilter) {
                    filteredLaptops.add(laptop);
                }
            }
        }

        if (filteredLaptops.isEmpty()) {
            System.out.println("Ноутбуки по заданным критериям не найдены.");
        } else {
            for (Laptop laptop : filteredLaptops) {
                System.out.println(laptop);
            }
        }
    }

    public static void main(String[] args) {
        NotebookShop shop = new NotebookShop();

        // Добавление ноутбуков в магазин
        shop.addLaptop("геймерские", new Laptop("Lenovo", 16, 1024, "Windows", "Черный"));
        shop.addLaptop("геймерские", new Laptop("Dell", 32, 2048, "Ubuntu", "Серебристый"));

        shop.addLaptop("офисные", new Laptop("HP", 8, 512, "Windows", "Белый"));
        shop.addLaptop("офисные", new Laptop("Asus", 12, 1024, "Windows", "Синий"));

        // Запрос пользователю и фильтрация ноутбуков
        Scanner scanner = new Scanner(System.in, "UTF-8");
        Map<String, Object> filters = new HashMap<>();
        System.out.println("Введите критерии фильтрации ноутбуков:");
        System.out.println("1 - Бренд");
        System.out.println("2 - Операционная система");
        System.out.println("3 - Оперативная память (Минимальное значение)");
        System.out.println("4 - Объем жесткого диска (Минимальное значение)");
        System.out.println("5 - Цвет");

        // Ввод критериев фильтрации
        System.out.print("Введите номер критерия: ");
        int criteriaNum;
        if (scanner.hasNextInt()) {
            criteriaNum = scanner.nextInt();
        } else {
            System.out.println("Некорректный ввод");
            return;
        }

        switch (criteriaNum) {
            case 1:
                System.out.print("Введите бренд: ");
                String brand = scanner.next();
                filters.put("brand", brand);
                break;
            case 2:
                System.out.print("Введите операционную систему: ");
                String os = scanner.next();
                filters.put("os", os);
                break;
            case 3:
                System.out.print("Введите минимальный объем оперативной памяти: ");
                int minRam;
                if (scanner.hasNextInt()) {
                    minRam = scanner.nextInt();
                    filters.put("ram", minRam);
                } else {
                    System.out.println("Некорректный ввод");
                    return;
                }
                break;
            case 4:
                System.out.print("Введите минимальный объем жесткого диска: ");
                int minHdd;
                if (scanner.hasNextInt()) {
                    minHdd = scanner.nextInt();
                    filters.put("hdd", minHdd);
                } else {
                    System.out.println("Некорректный ввод");
                    return;
                }
                break;
            case 5:
                System.out.print("Введите цвет: ");
                String color = scanner.next();
                filters.put("color", color);
                break;
            default:
                System.out.println("Некорректный ввод");
                return;
        }

        // Фильтрация ноутбуков
        shop.filterLaptops(filters);
    }
}
