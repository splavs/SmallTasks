-- Author Vyacheslav Silchenko

-- Task 13.1
-- 13.1	Написать процедуру, которая возвращает самый крупный заказ для каждого из продавцов за определенный год. В результатах не может быть несколько заказов одного продавца, должен быть только один и самый крупный. В результатах запроса должны быть выведены следующие колонки: колонка с именем и фамилией продавца (firstName и lastName – пример: Nancy Davolio), номер заказа и его стоимость. В запросе надо учитывать Discount при продаже товаров. Процедуре передается год, за который надо сделать отчет, и количество возвращаемых записей. Результаты запроса должны быть упорядочены по убыванию суммы заказа. Название процедуры GreatestOrders. Необходимо продемонстрировать использование этой процедуры.
-- Подсказка: для вывода результатов можно использовать DBMS_OUTPUT.
-- Также помимо демонстрации вызова процедуры в скрипте Query.sql надо написать отдельный ДОПОЛНИТЕЛЬНЫЙ проверочный запрос для тестирования правильности работы процедуры GreatestOrders. Проверочный запрос должен выводить в удобном для сравнения с результатами работы процедур виде для определенного продавца для всех его заказов за определенный указанный год в результатах следующие колонки: имя продавца, номер заказа, сумму заказа. Проверочный запрос не должен повторять запрос, написанный в процедуре, - он должен выполнять только то, что описано в требованиях по нему.
-- ВСЕ ЗАПРОСЫ ПО ВЫЗОВУ ПРОЦЕДУР ДОЛЖНЫ БЫТЬ НАПИСАНЫ В ФАЙЛЕ Query.sql – см. пояснение ниже в разделе: Error! Reference source not found..

CREATE OR REPLACE PROCEDURE GreatestOrders(p_year IN VARCHAR2, p_n_of_records IN NUMBER)
IS
  CURSOR v_full_select_cursor IS
    SELECT *
    FROM
      (SELECT
         (SELECT concat(firstname, lastname)
          FROM employees e0
          WHERE e0.employeeid = e.employeeid)           AS employeefullname,
         o.orderid,
         od.quantity * od.unitprice * (1 - od.discount) AS total
       FROM employees e LEFT JOIN orders o
           ON e.employeeid = o.employeeid
         LEFT JOIN order_details od
           ON o.orderid = od.orderid
       WHERE to_char(orderdate, 'YYYY') = p_year AND
             (e.employeeid, od.quantity * od.unitprice * (1 - od.discount)) IN
             (SELECT
                e2.employeeid,
                max(od2.quantity * od2.unitprice * (1 - od2.discount)) AS total
              FROM employees e2 LEFT JOIN orders o2
                  ON e2.employeeid = o2.employeeid
                LEFT JOIN order_details od2
                  ON o2.orderid = od2.orderid
              GROUP BY e2.employeeid
             )
       ORDER BY total DESC
      )
    WHERE rownum <= p_n_of_records;

  BEGIN
    FOR v_record IN v_full_select_cursor LOOP
      dbms_output.put_line(v_record.employeefullname || ' ' || v_record.orderid || ' ' || v_record.total);
    END LOOP;
  END;

-- Task 13.2
-- 13.2	Написать процедуру, которая возвращает заказы в таблице Orders, согласно указанному сроку доставки в днях (разница между orderDate и shippedDate).  В результатах должны быть возвращены заказы, срок которых превышает переданное значение или еще недоставленные заказы. Значению по умолчанию для передаваемого срока 35 дней. Название процедуры ShippedOrdersDiff. Процедура должна выводить следующие колонки: orderID, orderDate, shippedDate, ShippedDelay (разность в днях между shippedDate и orderDate), specifiedDelay (переданное в процедуру значение).  Результат должен быть отсортирован по shippedDelay.
-- Подсказка: для вывода результатов можно использовать DBMS_OUTPUT.
-- Необходимо продемонстрировать использование этой процедуры.


CREATE OR REPLACE PROCEDURE ShippedOrdersDiff(p_shippedDelay IN NUMBER DEFAULT 35) IS
  CURSOR v_shipped_delay_cursor IS
    SELECT
      orderid,
      orderdate,
      shippeddate,
      shippeddate - orderdate AS ShippedDelay
    FROM orders
    WHERE shippeddate - orderdate > p_shippeddelay
    ORDER BY shippeddelay;

  BEGIN

    FOR v_record IN v_shipped_delay_cursor LOOP
      dbms_output.put_line(v_record.orderid || ' ' || v_record.orderDate || ' ' || v_record.shippeddate || ' ' || v_record.shippeddelay);
    END LOOP;
  END;

-- Task 13.3
-- 13.3	Написать процедуру, которая выводит всех подчиненных заданного продавца, как непосредственных, так и подчиненных его подчиненных. В качестве входного параметра процедуры используется employeeID. Необходимо вывести столбцы employeeID, имена подчиненных и уровень вложенности согласно иерархии подчинения. Продавец, для которого надо найти подчиненных также должен быть высвечен. Название процедуры SubordinationInfo. Необходимо использовать конструкцию START WITH … CONNECT BY.
CREATE OR REPLACE PROCEDURE SubordinationInfo(p_employee_id IN NUMBER) IS
  CURSOR v_subordination_cursor IS
    SELECT
      employeeid,
      firstname,
      lastname
    FROM employees
    START WITH employeeid = p_employee_id
    CONNECT BY PRIOR employeeid = reportsto;

  BEGIN
    FOR v_record IN v_subordination_cursor LOOP
      dbms_output.put_line(v_record.employeeid || ' ' || v_record.firstname || ' ' || v_record.lastname);
    END LOOP;

  END;

-- Task 13.4
-- 13.4	Написать функцию, которая определяет, есть ли у продавца подчиненные и возвращает их количество - тип данных INTEGER. В качестве входного параметра функции используется employeeID. Название функции IsBoss. Продемонстрировать использование функции для всех продавцов из таблицы Employees.
CREATE OR REPLACE FUNCTION IsBoss(p_employee_id IN NUMBER) RETURN INTEGER IS
  CURSOR v_subordination_cursor IS
    SELECT
      employeeid,
      firstname,
      lastname
    FROM employees
    START WITH employeeid = p_employee_id
    CONNECT BY PRIOR employeeid = reportsto;

  v_counter NUMBER;
  BEGIN
    v_counter := 0;
    FOR v_record IN v_subordination_cursor LOOP
      v_counter := v_counter + 1;
    END LOOP;
    RETURN v_counter - 1;

  END;