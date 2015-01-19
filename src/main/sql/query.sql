-- Author Vyacheslav Silchenko

-- Task 1.1
SELECT
  orderId,
  to_char(shippedDate, 'YYYY-MM-DD') AS "shippedDate",
  shipVia
FROM Orders
WHERE shippedDate > to_date('1998-05-05', 'YYYY-MM-DD') AND shipVia >= 2;

-- Task 1.2
SELECT
  orderId,
  CASE WHEN shippedDate IS NULL THEN 'Not Shipped'
  END
    AS "shippedDate"
FROM Orders
WHERE shippedDate IS NULL;

-- Task 1.3
SELECT
  orderId as "Order Number",
  CASE WHEN shippedDate IS NULL THEN 'Not Shipped'
  ELSE to_char(shippedDate,'DD.MM.YYYY')
  END
          AS "Shipped Date"
FROM Orders
WHERE shippedDate > to_date('1998-05-05', 'YYYY-MM-DD') OR shippedDate IS NULL;

-- Task 2.1
SELECT
  contactName,
  country
FROM Customers
WHERE country IN ('USA', 'Canada')
ORDER BY contactName, country;

-- Task 2.2
SELECT
  contactName,
  country
FROM Customers
WHERE country NOT IN ('USA', 'Canada')
ORDER BY contactName, country DESC;

-- Task 2.3
SELECT
  DISTINCT country
FROM Customers
ORDER BY country DESC;

-- Task 3.1
SELECT orderId
FROM Order_Details
WHERE quantity BETWEEN 3 AND 10;

-- Task 3.2
SELECT customerId, country
FROM Customers
WHERE country BETWEEN 'B%' AND 'H%'
ORDER BY country;

--Task3.3
SELECT customerId, country
FROM Customers
WHERE country >= 'B%' AND country <= 'H%'
ORDER BY country;
-- The same as soon as BETWEEN convert to >= and <=

-- Task 4.1
SELECT *
FROM Products
WHERE lower(productname) LIKE '%cho_olade%';

-- Task 5.1
COLUMN Totals FORMAT $9,999.99;
SELECT
  quantity * unitPrice * (1 - discount) AS "Totals"
FROM Order_Details;

-- Task 5.2
SELECT
  count(*) - count(shippeddate)
FROM Orders;

-- Task 5.3
SELECT
  count(DISTINCT customerId)
FROM Orders;

-- Task 6.1
SELECT
  to_char(orderDate, 'YYYY') as "Year",
  count(orderId) as "Total"
FROM Orders
GROUP BY to_char(orderDate, 'YYYY');

SELECT count(orderId)
FROM orders;

-- Task 6.2
SELECT
  (SELECT concat(firstname, lastname) FROM employees e WHERE e.employeeid = o.employeeid) AS "Seller",
  count(orderid) AS Amount
FROM Orders o
GROUP BY employeeid
ORDER BY Amount DESC;

-- Task 6.3
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
SELECT
  DECODE(GROUPING(employeeId), 1, 'ALL', (SELECT concat(firstname, lastName) FROM employees e WHERE e.employeeid = o.employeeid)) AS Seller,
  DECODE(GROUPING(customerid), 1, 'ALL', (SELECT contactname FROM customers c WHERE c.customerid = o.customerid)) AS Customer,
  count(orderid) AS Amount
FROM Orders o
GROUP BY ROLLUP (employeeId, o.customerId)
ORDER BY Amount DESC, Customer, Seller;

-- Task 6.5
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
SELECT
  e1.lastName AS "User Name",
  e2.lastName AS "Boss"
FROM Employees e1 INNER JOIN Employees e2
ON e1.reportsto = e2.employeeid;

-- Task 7.1
SELECT
  lastname,
  territorydescription
FROM employeeterritories NATURAL INNER JOIN employees NATURAL INNER JOIN territories NATURAL INNER JOIN region
WHERE regiondescription = 'Western';

-- Task 8.1

SELECT
  contactname,
  count(orderid) as Count
FROM customers c LEFT OUTER JOIN orders o
ON c.customerid = o.customerid
GROUP BY contactname
ORDER BY Count;

-- Task 9.1
SELECT
  companyName
FROM suppliers
WHERE supplierid IN (SELECT supplierid FROM products WHERE unitsinstock = 0);
-- No, as inner sql produced more than one value.

-- Task 10.1
SELECT *
FROM Employees e
WHERE
  150 < (SELECT count(*) FROM orders o WHERE e.employeeid = o.employeeid );

-- Task 11.1
SELECT contactname
FROM customers c
WHERE NOT EXISTS
(SELECT * FROM orders o WHERE c.customerid = o.customerid);

-- Task 12.1
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
