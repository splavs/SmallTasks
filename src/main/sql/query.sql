-- Author Vyacheslav Silchenko

-- Task 1.1
-- 1.1	Выбрать из таблицы Orders заказы, которые были доставлены после 5 мая 1998 года (колонка shippedDate) включительно и которые доставлены с shipVia >= 2. Формат указания даты должен быть верным при любых региональных настройках. Этот метод использовать далее для всех заданий. Запрос должен выбирать только колонки orderID, shippedDate и shipVia.
-- Пояснить, почему сюда не попали заказы с NULL-ом в колонке shippedDate.
SELECT
  orderId,
  to_char(shippedDate, 'YYYY-MM-DD') AS "shippedDate",
  shipVia
FROM Orders
WHERE shippedDate > to_date('1998-05-05', 'YYYY-MM-DD') AND shipVia >= 2;

-- Task 1.2
-- 1.2	Написать запрос, который выводит только недоставленные заказы из таблицы Orders. В результатах запроса высвечивать для колонки shippedDate вместо значений NULL строку ‘Not Shipped’ – необходимо использовать CASЕ выражение. Запрос должен выбирать только колонки orderID и shippedDate.
SELECT
  orderId,
  CASE WHEN shippedDate IS NULL THEN 'Not Shipped'
  END
    AS "shippedDate"
FROM Orders
WHERE shippedDate IS NULL;

-- Task 1.3
-- 1.3	Выбрать из таблици Orders заказы, которые были доставлены после 5 мая 1998 года (shippedDate), не включая эту дату, или которые еще не доставлены. Запрос должен выбирать только колонки orderID (переименовать в Order Number) и shippedDate (переименовать в Shipped Date). В результатах запроса  для колонки shippedDate вместо значений NULL выводить строку ‘Not Shipped’ (необходимо использовать функцию NVL), для остальных значений высвечивать дату в формате “ДД.ММ.ГГГГ”.
SELECT
  orderId as "Order Number",
  CASE WHEN shippedDate IS NULL THEN 'Not Shipped'
  ELSE to_char(shippedDate,'DD.MM.YYYY')
  END
          AS "Shipped Date"
FROM Orders
WHERE shippedDate > to_date('1998-05-05', 'YYYY-MM-DD') OR shippedDate IS NULL;

-- Task 2.1
-- 2.1	Выбрать из таблицы Customers всех заказчиков, проживающих в USA или Canada. Запрос сделать с только помощью оператора IN. Запрос должен выбирать колонки с именем пользователя и названием страны. Упорядочить результаты запроса по имени заказчиков и по месту проживания.
SELECT
  contactName,
  country
FROM Customers
WHERE country IN ('USA', 'Canada')
ORDER BY contactName, country;

-- Task 2.2
-- 2.2	Выбрать из таблицы Customers всех заказчиков, не проживающих в USA и Canada. Запрос сделать с помощью оператора IN. Запрос должен выбирать колонки с именем пользователя и названием страны а. Упорядочить результаты запроса по имени заказчиков в порядке убывания.
SELECT
  contactName,
  country
FROM Customers
WHERE country NOT IN ('USA', 'Canada')
ORDER BY contactName, country DESC;

-- Task 2.3
-- 2.3	Выбрать из таблицы Customers все страны, в которых проживают заказчики. Страна должна быть упомянута только один раз, Результат должен быть отсортирован по убыванию. Не использовать предложение GROUP BY.
SELECT
  DISTINCT country
FROM Customers
ORDER BY country DESC;

-- Task 3.1
-- 3.1	Выбрать все заказы из таблицы Order_Details (заказы не должны повторяться), где встречаются продукты с количеством от 3 до 10 включительно – это колонка Quantity в таблице Order_Details. Использовать оператор BETWEEN. Запрос должен выбирать только колонку идентификаторы заказов.
SELECT orderId
FROM Order_Details
WHERE quantity BETWEEN 3 AND 10;

-- Task 3.2
-- 3.2	Выбрать всех заказчиков из таблицы Customers, у которых название страны начинается на буквы из диапазона B и G. Использовать оператор BETWEEN. Проверить, что в результаты запроса попадает Germany. Запрос должен выбирать только колонки сustomerID  и сountry. Результат должен быть отсортирован по значению столбца сountry.
SELECT customerId, country
FROM Customers
WHERE country BETWEEN 'B%' AND 'H%'
ORDER BY country;

--Task3.3
-- 3.3	Выбрать всех заказчиков из таблицы Customers, у которых название страны начинается на буквы из диапазона B и G, не используя оператор BETWEEN. Запрос должен выбирать только колонки сustomerID  и сountry. Результат должен быть отсортирован по значению столбца сountry. С помощью опции “Execute Explain Plan” определить какой запрос предпочтительнее 3.2 или 3.3. В комментариях к текущему запросу необходимо объяснить результат.
SELECT customerId, country
FROM Customers
WHERE country >= 'B%' AND country <= 'H%'
ORDER BY country;
-- The same as soon as BETWEEN convert to >= and <=

-- Task 4.1
-- 4.1	В таблице Products найти все продукты (колонка productName), где встречается подстрока 'chocolade'. Известно, что в подстроке 'chocolade' может быть изменена одна буква 'c' в середине - найти все продукты, которые удовлетворяют этому условию. Подсказка: в результате должны быть найдены 2 строки.
SELECT *
FROM Products
WHERE lower(productname) LIKE '%cho_olade%';

-- Task 5.1
-- 5.1	Найти общую сумму всех заказов из таблицы Order_Details с учетом количества закупленных товаров и скидок по ним. Результат округлить до сотых и отобразить в стиле: $X,XXX.XX, где “$” - валюта доллары, “,” – разделитель групп разрядов, “.” – разделитель целой и дробной части, при этом дробная часть должна содержать цифры до сотых, пример: $1,234.00. Скидка (колонка Discount) составляет процент из стоимости для данного товара. Для определения действительной цены на проданный продукт надо вычесть скидку из указанной в колонке unitPrice цены. Результатом запроса должна быть одна запись с одной колонкой с названием колонки 'Totals'.
COLUMN Totals FORMAT $9,999.99;
SELECT
  quantity * unitPrice * (1 - discount) AS "Totals"
FROM Order_Details;

-- Task 5.2
-- 5.2	По таблице Orders найти количество заказов, которые еще не были доставлены (т.е. в колонке shippedDate нет значения даты доставки). Использовать при этом запросе только оператор COUNT. Не использовать предложения WHERE и GROUP.
SELECT
  count(*) - count(shippeddate)
FROM Orders;

-- Task 5.3
-- 5.3	По таблице Orders найти количество различных покупателей (сustomerID), сделавших заказы. Использовать функцию COUNT и не использовать предложения WHERE и GROUP.
SELECT
  count(DISTINCT customerId)
FROM Orders;

-- Task 6.1
-- 6.1	По таблице Orders найти количество заказов с группировкой по годам. Запрос должен выбирать две колонки c названиями Year и Total. Написать проверочный запрос, который вычисляет количество всех заказов.
SELECT
  to_char(orderDate, 'YYYY') as "Year",
  count(orderId) as "Total"
FROM Orders
GROUP BY to_char(orderDate, 'YYYY');

SELECT count(orderId)
FROM orders;

-- Task 6.2
-- 6.2	По таблице Orders найти количество заказов, cделанных каждым продавцом. Заказ для указанного продавца – это любая запись в таблице Orders, где в колонке employeeID задано значение для данного продавца. Запрос должен выбирать колонку с полным именем продавца (получается конкатенацией lastName & firstName из таблицы Employees)  с названием колонки ‘Seller’ и колонку c количеством заказов с названием 'Amount'. Полное имя продавца должно быть получено отдельным запросом в колонке основного запроса (после SELECT, до FROM). Результаты запроса должны быть упорядочены по убыванию количества заказов.
SELECT
  (SELECT concat(firstname, lastname) FROM employees e WHERE e.employeeid = o.employeeid) AS "Seller",
  count(orderid) AS Amount
FROM Orders o
GROUP BY employeeid
ORDER BY Amount DESC;

-- Task 6.3
-- 6.3	Выбрать 5 стран, в которых проживает наибольшее количество заказчиков. Список должен быть отсортирован по убыванию популярности. Запрос должен выбирать два столбца - сountry и Count (количество заказчиков).
SELECT *
FROM (
  SELECT
    shipcountry                AS "Country",
    count(DISTINCT customerid) AS Count
  FROM orders
  GROUP BY shipcountry
  ORDER BY Count DESC
) WHERE rownum <= 5;

-- for Oracle from 12 version only
SELECT
  shipcountry                AS "Country",
  count(DISTINCT customerid) AS Count
FROM orders
GROUP BY shipcountry
ORDER BY Count DESC
FETCH FIRST 5 ROWS ONLY;

-- Task 6.4
-- 6.4	По таблице Orders найти количество заказов, cделанных каждым продавцом и для каждого покупателя. Необходимо определить это только для заказов, сделанных в 1998 году. Запрос должен выбирать колонку с именем продавца (название колонки ‘Seller’), колонку с именем покупателя (название колонки ‘Customer’) и колонку c количеством заказов высвечивать с названием 'Amount'. В запросе необходимо использовать специальный оператор языка PL/SQL для работы с выражением GROUP (Этот же оператор поможет выводить строку “ALL” в результатах запроса). Группировки должны быть сделаны по ID продавца и покупателя. Результаты запроса должны быть упорядочены по продавцу, покупателю и по убыванию количества продаж. В результатах должна быть сводная информация по продажам. Т.е. в результирующем наборе должны присутствовать дополнительно к информации о продажах продавца для каждого покупателя следующие строчки:
SELECT
  DECODE(GROUPING(employeeId), 1, 'ALL', (SELECT concat(firstname, lastName) FROM employees e WHERE e.employeeid = o.employeeid)) AS Seller,
  DECODE(GROUPING(customerid), 1, 'ALL', (SELECT contactname FROM customers c WHERE c.customerid = o.customerid)) AS Customer,
  count(orderid) AS Amount
FROM Orders o
GROUP BY ROLLUP (employeeId, o.customerId)
ORDER BY Amount DESC, Customer, Seller;

-- Task 6.5
-- 6.5	Найти покупателей и продавцов, которые живут в одном городе. Если в городе живут только продавцы или только покупатели, то информация о таких покупателях и продавцах не должна попадать в результирующий набор. Не использовать конструкцию JOIN или декартово произведение. В результатах запроса необходимо вывести следующие заголовки для результатов запроса: ‘Person’, ‘Type’ (здесь надо выводить строку ‘Customer’ или  ‘Seller’ в зависимости от типа записи), ‘City’. Отсортировать результаты запроса по колонкам ‘City’ и ‘Person’.
SELECT
  contactname AS Person,
  'Seller' AS Type,
  city AS City
FROM Customers
WHERE city IN (SELECT city FROM Employees)
UNION
SELECT
  concat(firstname, lastname) AS Person,
  'Customer' AS Type,
  city AS City
FROM Employees
WHERE city IN (SELECT city FROM Customers);

-- Task 6.6
-- 6.6	Найти всех покупателей, которые живут в одном городе. В запросе использовать соединение таблицы Customers c собой - самосоединение. Высветить колонки сustomerID  и City. Запрос не должен выбирать дублируемые записи. Отсортировать результаты запроса по колонке City. Для проверки написать запрос, который выбирает города, которые встречаются более одного раза в таблице Customers. Это позволит проверить правильность запроса.
SELECT DISTINCT
  c1.customerId,
  c1.city
FROM Customers c1 INNER JOIN Customers c2
ON c1.city = c2.city AND c1.customerid <> c2.customerid
ORDER BY city;

SELECT city
FROM Customers
GROUP BY city
HAVING count(city) > 1;

-- Task 6.7
-- 6.7	По таблице Employees найти для каждого продавца его руководителя, т.е. кому он делает репорты. Высветить колонки с именами 'User Name' (lastName) и 'Boss'. Имена должны выбираться из колонки lastName. Выбираются ли все продавцы в этом запросе?
SELECT
  e1.lastName AS "User Name",
  e2.lastName AS "Boss"
FROM Employees e1 INNER JOIN Employees e2
ON e1.reportsto = e2.employeeid;

-- Task 7.1
-- 7.1	Определить продавцов, которые обслуживают регион 'Western' (таблица Region). Запрос должен выбирать: 'lastName' продавца и название обслуживаемой территории (столбец territoryDescription из таблицы Territories). Запрос должен использовать JOIN в предложении FROM. Для определения связей между таблицами Employees и Territories надо использовать графическую схему для базы Southwind.
SELECT
  lastname,
  territorydescription
FROM employeeterritories NATURAL INNER JOIN employees NATURAL INNER JOIN territories NATURAL INNER JOIN region
WHERE regiondescription = 'Western';

-- Task 8.1
-- 8.1	Запрос должен выбирать имена всех заказчиков из таблицы Customers и суммарное количество их заказов из таблицы Orders. Принять во внимание, что у некоторых заказчиков нет заказов, но они также должны быть выведены в результатах запроса. Упорядочить результаты запроса по возрастанию количества заказов.
SELECT
  contactname,
  count(orderid) as Count
FROM customers c LEFT OUTER JOIN orders o
ON c.customerid = o.customerid
GROUP BY contactname
ORDER BY Count;

-- Task 9.1
-- 9.1	Запрос должен выбирать всех поставщиков (колонка companyName в таблице Suppliers), у которых нет хотя бы одного продукта на складе (unitsInStock в таблице Products равно 0). Использовать вложенный SELECT для этого запроса с использованием оператора IN. Можно ли использовать вместо оператора IN оператор '='?
SELECT
  companyName
FROM suppliers
WHERE supplierid IN (SELECT supplierid FROM products WHERE unitsinstock = 0);
-- No, as inner sql produced more than one value.

-- Task 10.1
-- 10.1	Запрос должен выбирать имена всех продавцов, которые имеют более 150 заказов. Использовать вложенный коррелированный SELECT.
SELECT *
FROM Employees e
WHERE
  150 < (SELECT count(*) FROM orders o WHERE e.employeeid = o.employeeid );

-- Task 11.1
-- 11.1	Запрос должен выбирать имена заказчиков (таблица Customers), которые не имеют ни одного заказа (подзапрос по таблице Orders). Использовать коррелированный SELECT и оператор EXISTS.
SELECT contactname
FROM customers c
WHERE NOT EXISTS
(SELECT * FROM orders o WHERE c.customerid = o.customerid);

-- Task 12.1
-- 12.1	Для формирования алфавитного указателя Employees необходимо написать запрос должен выбирать из таблицы Employees список только тех букв алфавита, с которых начинаются фамилии Employees (колонка lastName) из этой таблицы.  Алфавитный список должен быть отсортирован по возрастанию.
SELECT DISTINCT SUBSTR(lastName, 1, 1) AS Letter
FROM Employees
ORDER BY Letter;

-- Task 13.1
-- 13.1	Написать процедуру, которая возвращает самый крупный заказ для каждого из продавцов за определенный год. В результатах не может быть несколько заказов одного продавца, должен быть только один и самый крупный. В результатах запроса должны быть выведены следующие колонки: колонка с именем и фамилией продавца (firstName и lastName – пример: Nancy Davolio), номер заказа и его стоимость. В запросе надо учитывать Discount при продаже товаров. Процедуре передается год, за который надо сделать отчет, и количество возвращаемых записей. Результаты запроса должны быть упорядочены по убыванию суммы заказа. Название процедуры GreatestOrders. Необходимо продемонстрировать использование этой процедуры.
-- Подсказка: для вывода результатов можно использовать DBMS_OUTPUT.
-- Также помимо демонстрации вызова процедуры в скрипте Query.sql надо написать отдельный ДОПОЛНИТЕЛЬНЫЙ проверочный запрос для тестирования правильности работы процедуры GreatestOrders. Проверочный запрос должен выводить в удобном для сравнения с результатами работы процедур виде для определенного продавца для всех его заказов за определенный указанный год в результатах следующие колонки: имя продавца, номер заказа, сумму заказа. Проверочный запрос не должен повторять запрос, написанный в процедуре, - он должен выполнять только то, что описано в требованиях по нему.
-- ВСЕ ЗАПРОСЫ ПО ВЫЗОВУ ПРОЦЕДУР ДОЛЖНЫ БЫТЬ НАПИСАНЫ В ФАЙЛЕ Query.sql – см. пояснение ниже в разделе: Error! Reference source not found..
SELECT
  (SELECT concat(firstname, lastName) FROM employees e0 WHERE e0.employeeid = employees.employeeid) AS Seller,
  orders.orderid,
  od.quantity * od.unitprice * (1 - od.discount) AS total

FROM employees INNER JOIN orders
ON employees.employeeid = orders.employeeid
INNER JOIN order_details od
ON orders.orderid = od.orderid
ORDER BY Total DESC;

-- Task 13.2
-- 13.2	Написать процедуру, которая возвращает заказы в таблице Orders, согласно указанному сроку доставки в днях (разница между orderDate и shippedDate).  В результатах должны быть возвращены заказы, срок которых превышает переданное значение или еще недоставленные заказы. Значению по умолчанию для передаваемого срока 35 дней. Название процедуры ShippedOrdersDiff. Процедура должна выводить следующие колонки: orderID, orderDate, shippedDate, ShippedDelay (разность в днях между shippedDate и orderDate), specifiedDelay (переданное в процедуру значение).  Результат должен быть отсортирован по shippedDelay.
-- Подсказка: для вывода результатов можно использовать DBMS_OUTPUT.
-- Необходимо продемонстрировать использование этой процедуры.
SET SERVEROUTPUT ON;
DECLARE
  P_SHIPPEDDELAY NUMBER;
BEGIN
  P_SHIPPEDDELAY := 35;

  SHIPPEDORDERSDIFF(
      P_SHIPPEDDELAY => P_SHIPPEDDELAY
  );
END;

-- Task 13.3
-- 13.3	Написать процедуру, которая выводит всех подчиненных заданного продавца, как непосредственных, так и подчиненных его подчиненных. В качестве входного параметра процедуры используется employeeID. Необходимо вывести столбцы employeeID, имена подчиненных и уровень вложенности согласно иерархии подчинения. Продавец, для которого надо найти подчиненных также должен быть высвечен. Название процедуры SubordinationInfo. Необходимо использовать конструкцию START WITH … CONNECT BY.
SET SERVEROUTPUT ON;
DECLARE
  P_EMPLOYEE_ID NUMBER;
BEGIN
  P_EMPLOYEE_ID := 5;

  SUBORDINATIONINFO(
      P_EMPLOYEE_ID => P_EMPLOYEE_ID
  );
END;

-- Task 13.4
-- 13.4	Написать функцию, которая определяет, есть ли у продавца подчиненные и возвращает их количество - тип данных INTEGER. В качестве входного параметра функции используется employeeID. Название функции IsBoss. Продемонстрировать использование функции для всех продавцов из таблицы Employees.
SET SERVEROUTPUT ON;
DECLARE
  P_EMPLOYEE_ID NUMBER;
  v_Return NUMBER;
BEGIN
  P_EMPLOYEE_ID := 5;

  v_Return := ISBOSS(
      P_EMPLOYEE_ID => P_EMPLOYEE_ID
  );

  DBMS_OUTPUT.PUT_LINE('v_Return = ' || v_Return);

END;
