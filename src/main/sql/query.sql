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

