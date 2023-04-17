import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Menu implements Finals {

    public static void menuLogin(List<Transaction> transactionList,
                                 List<Currency> currencyList, Map<String, String> userData) throws IOException, InterruptedException {
        boolean isLogin = false;
        do {
            System.out.print("""
                      
                      𝟙. Войти под уже существующей учётной записью
                      𝟚. Зарегистрировать нового пользователя
                    Введите номер пункта меню:\u202F""");
            int choice = Input.readIntLimited(1, 2);
            switch (choice) {
                case 1 -> {
                    isLogin = Input.userLoginRead(userData);
                    if (isLogin) {
                        Input.readFromEncryptFile(transactionList, currencyList);
                    }
                }
                case 2 -> {
                    isLogin = Input.userNewRead(userData);
                    if (isLogin) {
                        Input.initializeData(currencyList);
                    }
                }
            }
        } while (!isLogin);
    }

    public static void menuMain(List<Transaction> transactionList,
                                List<Currency> currencyList) throws IOException, AWTException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            Output.clearScreen();
            System.out.println(LOGO2);
            System.out.println();
            System.out.println(BLUE + "[ ОСНОВНОЕ МЕНЮ ]" + RESET);
            System.out.println("""
                    Доступные действия:
                      𝟙. Создать новую запись
                      𝟚. Посмотреть все записи одним списком
                      𝟛. Посмотреть все записи списками по 10
                      𝟜. Посмотреть записи за указанный период времени
                      𝟝. Отфильтровать записи по выбранному критерию
                      𝟞. Добавить новую категорию
                      𝟟. Добавить новую валюту
                      𝟠. Редактировать категории/валюты
                      𝟡. Сохранить все данные в файл
                      
                      𝟘. Выход
                    """);
            System.out.print("Введите номер пункта меню: ");
            int choice = Input.readIntLimited(0, 9);
            switch (choice) {
                case 1 -> Input.addTransaction(br, transactionList, currencyList);
                case 2 -> Output.printTransactionAll(br, transactionList, currencyList);
                case 3 -> Output.printTransactionBy10(transactionList, currencyList);
                case 4 -> menuPrintTransactionByDate(br, transactionList, currencyList);
                case 5 -> menuFilterTransaction(br, transactionList, currencyList);

                //todo Отфильтровать записи по выбранному критерию / изменить вывод / добавить темпсумму в валюты

                case 6 -> Input.addCategory(br);
                case 7 -> Input.addCurrency(br, currencyList);
                case 8 -> menuEditCategoryCurrency(br, currencyList);
                case 9 -> Output.writeToEncryptFile(transactionList, currencyList);
                case 0 -> System.exit(0); //todo меню для выхода с сохранением данных
                default -> System.out.println();
            }
        }
    }

    public static void menuFilterTransaction(BufferedReader br, List<Transaction> transactionList,
                                             List<Currency> currencyList) throws IOException, InterruptedException, AWTException {
        System.out.println();
        System.out.println(BLUE + "[ НАСТРОЙКИ ФИЛЬТРА ]" + RESET);
        System.out.println("""
                Выберите критерий для отбора записей:
                  𝟙. Отфильтровать по типу операции (доход/расход)
                  𝟚. Отфильтровать по категории операции
                  𝟛. Отфильтровать по валюте операции
                  𝟜. Вернуться в главное меню""");
        System.out.print("Введите номер пункта меню: ");
        int choice = Input.readIntLimited(1, 4);
        switch (choice) {
            case 1 -> Output.printTransactionFilteredByType(br, transactionList, currencyList);
            case 2 -> System.out.println();
            case 3 -> System.out.println(); //todo
            case 4 -> System.out.println();
        }

    }

    public static void menuEditCategoryCurrency(BufferedReader br, List<Currency> currencyList) throws IOException {
        System.out.println();
        System.out.println(BLUE + "[ ВЫБОР ОБЪЕКТА ДЛЯ РЕДАКТИРОВАНИЯ ]" + RESET);
        System.out.println("""
                Что Вы хотите отредактировать:
                  𝟙. Редактировать категорию
                  𝟚. Редактировать валюту
                  𝟛. Вернуться в главное меню""");
        System.out.print("Введите номер пункта меню: ");
        int choice = Input.readIntLimited(1, 3);
        switch (choice) {
            case 1 -> Input.editCategory(br);
            case 2 -> Input.editCurrency(br, currencyList);
            default -> System.out.println();
        }
    }

    public static void menuPrintTransactionByDate(BufferedReader br, List<Transaction> transactionList,
                                                  List<Currency> currencyList) throws IOException, InterruptedException, AWTException {
        System.out.println();
        System.out.println(BLUE + "[ ВЫБОР ПЕРИОДА ДЛЯ ОТОБРАЖЕНИЯ ]" + RESET);
        System.out.println();
        Date firstDate;
        Date lastDate;
        do {
            do {
                System.out.print("Введите начальную дату в формате [дд.ММ.гггг]: ");
                firstDate = Input.dateFromString(br.readLine() + "  00:00");
            } while (firstDate == null);
            do {
                System.out.print("Введите конечную дату в формате [дд.ММ.гггг]: ");
                lastDate = Input.dateFromString(br.readLine() + "  23:59");
            } while (lastDate == null);
            if (firstDate.after(lastDate)) {
                System.out.println(RED + "Начальная дата не может быть позже конечной!" + RESET);
            }
        } while (firstDate.after(lastDate));
        Output.printTransactionByDate(br, transactionList, currencyList, firstDate, lastDate);
    }

    public static void menuAnderTransactionList(BufferedReader br, List<Transaction> transactionList,
                                                List<Currency> currencyList, boolean isList10) throws IOException, InterruptedException, AWTException {
        System.out.println("""
                Доступные действия:
                  𝟙. Просмотреть запись по Id
                  𝟚. Удалить запись по Id
                  𝟛. Вернуться в главное меню
                  𝟜. Продолжить просмотр станиц""");
        System.out.print("Введите номер пункта меню: ");
        int choice = Input.readIntLimited(1, 4);
        switch (choice) {
            case 1 -> {
                System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
                Output.showTransactionById(br, transactionList, currencyList, Input.readIntLimited(1, transactionList.size()), isList10);
            }
            case 2 -> {
                System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
                Input.deleteTransaction(transactionList, transactionList.size() - Input.readIntLimited(1, transactionList.size()));
            }
            case 3 -> Menu.menuMain(transactionList, currencyList);
            case 4 -> System.out.println(" ");

        }
    }

    public static void menuAfterTransactionListEnds(BufferedReader br, List<Transaction> transactionList,
                                                    List<Currency> currencyList, boolean isList10) throws IOException, InterruptedException, AWTException {
        System.out.println("""
                Доступные действия:
                  𝟙. Просмотреть запись по Id
                  𝟚. Удалить запись по Id
                  𝟛. Вернуться в главное меню""");
        System.out.print("Введите номер пункта меню: ");
        int choice = Input.readIntLimited(1, 3);
        switch (choice) {
            case 1 -> {
                System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
                Output.showTransactionById(br, transactionList, currencyList, Input.readIntLimited(1, transactionList.size()), isList10);
            }
            case 2 -> {
                System.out.print(BLUE + "Введите Id нужной записи: " + RESET);
                Input.deleteTransaction(transactionList, transactionList.size() - Input.readIntLimited(1, transactionList.size()));
            }
            case 3 -> Menu.menuMain(transactionList, currencyList);
        }
    }

    public static void menuAnderTransactionView(BufferedReader br, List<Transaction> transactionList,
                                                List<Currency> currencyList, int index, boolean isList10) throws IOException, InterruptedException, AWTException {
        System.out.println("""
                Доступные действия:
                  𝟙. Удалить эту запись
                  𝟚. Редактировать эту запись
                  𝟛. Вернуться в главное меню""");
        int choice;
        if (isList10) {
            System.out.println("  𝟜. Продолжить просмотр станиц");
            System.out.print("Введите номер пункта меню: ");
            choice = Input.readIntLimited(1, 4);
        } else {
            System.out.print("Введите номер пункта меню: ");
            choice = Input.readIntLimited(1, 3);
        }
        switch (choice) {
            case 1 -> Input.deleteTransaction(transactionList, index);
            case 2 -> Input.editTransaction(br, transactionList, currencyList, index);
            case 3 -> Menu.menuMain(transactionList, currencyList);
            case 4 -> System.out.print(" ");
        }
    }

}
