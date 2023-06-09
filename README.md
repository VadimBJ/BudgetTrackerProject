#  Budget Tracker project

## Описание программы
**Budget Tracker** - это удобное консольное приложение, разработанное на языке программирования Java, 
которое поможет Вам легко и быстро управлять своими финансами.

С помощью нашего приложения Вы сможете следить за своими финансами, создавая новые записи о доходах и расходах, 
а также анализировать свои записи за определенный период времени.

В главном меню приложения вы найдете все необходимые инструменты, для управления своими финансами. 
**Budget Tracker** обеспечивает простой и удобный интерфейс для взаимодействия с пользователем, позволяя гибко настраивать 
программу под свои нужды.

Одним из ключевых преимуществ **Budget Tracker** является широкий выбор доступных вариантов просмотра ваших финансовых 
операций. Для более удобного просмотра записей, **Budget Tracker** предоставляет возможность отображать записи в виде 
одного списка или в виде списков по 10 записей на странице. Это упрощает навигацию и обеспечивает более удобную 
работу с большим количеством данных. Вы также можете просмотреть записи за определенный период времени или 
отфильтровать их по выбранному критерию.

**Budget Tracker** обладает гибкой настройкой фильтров, которые позволяют 
пользователю отображать только те записи, которые соответствуют выбранным критериям. Например, пользователь может 
отфильтровать записи по категории, типу операции (расходы или доходы), валюте или временному периоду.

Все данные в файлах хранятся в зашифрованном виде, что позволяет с одной стороны хранить все Ваши финансовые данные 
на вашем компьютере и восстанавливать их в любой момент времени, а с другой – гарантирует безопасность и 
конфиденциальность Ваших данных.

**Budget Tracker** - это удобное и функциональное приложение, созданное для того, чтобы помочь Вам эффективно 
управлять своими финансами и получать максимальную выгоду от использования его возможностей.

## Структура основных классов, а так же способы их хранения в проекте

![Структура основных классов, а так же способы их хранения в проекте](https://i.ibb.co/LvW55Zd/3.jpg)

## class User
Данный класс представляет собой модель пользователя с логином и паролем, где пароль хранится в виде хеш-кода. 
Конструктор класса принимает логин и пароль в чистом виде, затем используется метод passwordHash() для получения 
хеш-кода пароля. В классе также определен статический метод makeHash(), который позволяет получить хеш-код пароля 
в виде строки. Для генерации хеш-кода пароля используется алгоритм SHA-256. Все байты хеш-кода конвертируются
в шестнадцатеричный формат с помощью метода bytesToHex(). Класс User предоставляет публичные методы для получения 
логина и хеш-кода пароля.

## class Currency 
Данный класс описывает валюту, которая имеет название (title), акроним (acronym), общее количество (total) 
и временное количество (tempTotal). Поле **tempTotal** используется для временных подсчетов. 
В конструкторе задаются название, акроним (абревиатура) и общее количество валюты. 
Класс содержит метод toString(), который возвращает строку, представляющую текущее состояние объекта в виде 
элемента таблицы. 

## class Category 
Данный класс представляет собой модель категории для расходов/доходов. 
Он содержит одно поле title, которое хранит название категории.
Этот класс используется для создания списка доступных категорий расходов и доходов в приложении, 
а также для связывания расходов или доходов с соответствующей категорией. Этот клас неотъемлемо связан 
с **enum TransactionType**.

## enum TransactionType
Это класс-перечисление (enum), которое представляет типы транзакций: доход (INCOMING) и расход (OUTGOING). 
У каждого типа транзакции есть идентификатор (id), заголовок (title) и список категорий (categoryList). 
Категории представлены классом **Category**. Это перечисление используется, чтобы определить тип транзакции 
при создании объекта **Transaction** и предоставляет доступ к списку категорий, связанных с каждым типом транзакции.

## class Transaction 
Данный класс описывает отдельную финансовую транзакцию, которая может быть как доходом (INCOMING), 
так и расходом (OUTGOING), имеет определенную категорию (Category) и валюту (Currency).

Конструктор класса принимает такие входные параметры для инициализации полей транзакции:
+ Название транзакции (title);
+ Описание транзакции (description);
+ Тип транзакции (INCOMING или OUTGOING);
+ Категория транзакции (category);
+ Валюта транзакции (currency);
+ Сумма транзакции (amount);
+ Дата транзакции (date).

Класс реализует интерфейс Comparable<Transaction> для сравнения транзакций по дате, в методе compareTo() и имеет 
два метода для вывода информации о транзакции в консоль:
+ toString() - выводит информацию о транзакции в формате таблицы;
+ printString(int i) - выводит информацию о транзакции в формате таблицы, включая номер строки.

## class Menu 
Данный класс представляет собой меню-интерфейс программы для управления финансовыми транзакциями. 
Он содержит статические методы для отображения различных меню и обработки ввода пользователя.
Класс **Menu** использует такие классы как **Input** и **Output** для обработки пользовательского ввода, отображения 
данных о транзакциях и валютах.

## class Input
Данный класс представляет утилитарный класс, который предназначен для чтения пользовательского ввода, 
инициализации данных по умолчанию и загрузки пользовательских данных из файла. 
Класс реализует интерфейс Finals, который содержит константные значения, используемые во всей программе. 
Класс также имеет статическую переменную User, которая хранит информацию о текущем пользователе.

## class Output
Данный класс представляет утилитарный класс, который предназначен для вывода информации на экран и сохранения ее в файл
в зашифрованном виде. Класс так же реализует интерфейс Finals, который содержит константные значения, используемые 
во всей программе. 

## class Encryption 
Данный класс содержит статические методы для шифрования и дешифрования строки с помощью шифра Цезаря, который 
заключается в замене каждого символа входной строки на символ, который находится на определенном расстоянии (смещении)
от него в алфавите. В данной реализации шифра Цезаря символы заменяются на символы, сдвинутые на shift позиций вперед 
по таблице символов Юникода.