# 《SQL练习》

***

> **学院：省级示范性软件学院**
>
> **题目:《SQL练习》**
>
> **姓名：刘顺文**
>
> **学号：2200770061**
>
> **班级：软工2202**
>
> **日期：2024-09-27**
>
> **实验环境： MySQL8.0  Navicat**

***

## 一. 作业要求与提示

> **作业要求：至少做出25道员工信息练习题和40道学生选课题**
>
> **作业提示：**排名可以使用Rank()窗口函数，结合 CTX 表达式实现，例如：练习中的第48题
>
> *WITH ranked_employees AS (*
>
> ​    *SELECT e.\*,*
>
> ​           *d.dept_name,*
>
> ​           *DENSE_RANK() OVER (PARTITION BY e.dept_id ORDER BY e.salary DESC) AS salary_rank*
>
> ​    *FROM employees e*
>
> ​    *JOIN departments d ON e.dept_id = d.dept_id*
>
> *)*
>
> *SELECT emp_id, first_name, last_name, dept_name, salary*
>
> *FROM ranked_employees*
>
> *WHERE salary_rank <= 3*
>
> *ORDER BY dept_name, salary DESC;*
>
> **截止日期：**2024-10-20

***

### 1.  emp.sql练习

> 1. 查询所有员工的姓名、邮箱和工作岗位。
>
>    ```sql
>    SELECT CONCAT(e.first_name," ",e.last_name) AS 姓名, e.email, e.job_title
>    FROM employees e;
>    ```
>
> 
>
> 2. 查询所有部门的名称和位置。
>
>    ```sql
>    SELECT  dep.dept_name 部门名称, dep.location 部门位置
>    FROM departments dep;
>    ```
>
> 
>
> 3. 查询工资超过70000的员工姓名和工资。
>
>    ```sql
>    SELECT CONCAT(e.first_name," ",e.last_name) AS 姓名, e.salary
>    FROM employees e
>    WHERE e.salary > 70000;
>    ```
>
> 
>
> 4. 查询IT部门的所有员工。
>
>    ```sql
>    SELECT e.emp_id,e.email,CONCAT(e.first_name," ",e.last_name) AS 姓名,e.hire_date,e.job_title
>    FROM employees e, departments dep
>    WHERE e.dept_id = dep.dept_id AND dept_name = "IT";
>    ```
>
> 
>
> 5. 查询入职日期在2020年之后的员工信息。
>
>    ```sql
>    SELECT e.emp_id,e.email,CONCAT(e.first_name," ",e.last_name) AS 姓名,e.hire_date,e.job_title
>    FROM employees e
>    WHERE e.hire_date > "2020-12-30";
>    ```
>
> 
>
> 6. 计算每个部门的平均工资。
>
>    ```sql
>    SELECT dep.dept_name, AVG(e.salary) AS 平均工资
>    FROM employees e, departments dep
>    WHERE e.dept_id = dep.dept_id
>    GROUP BY e.dept_id;
>    ```
>
> 
>
> 7. 查询工资最高的前3名员工信息。
>
>    ```sql
>    SELECT e.emp_id,e.email,CONCAT(e.first_name," ",e.last_name) AS 姓名,e.hire_date,e.job_title
>    FROM employees e
>    ORDER BY e.salary DESC
>    LIMIT 3;
>    ```
>
> 
>
> 8. 查询每个部门员工数量。
>
>    ```sql
>    SELECT dep.dept_name,COUNT(e.emp_id) AS 员工数量
>    FROM employees e, departments dep
>    WHERE e.dept_id = dep.dept_id
>    GROUP BY e.dept_id;
>    ```
>
> 
>
> 9. 查询没有分配部门的员工。
>
>    ```sql
>    SELECT e.emp_id,e.email,
>    CONCAT(e.first_name," ",e.last_name) AS 姓名,
>    e.hire_date,e.job_title
>    FROM employees e
>    WHERE e.dept_id IS NULL;
>    ```
>
> 
>
> 10. 查询参与项目数量最多的员工。
>
>     ```sql
>     WITH pro_num_emp AS (
>     	SELECT e.emp_id,
>         CONCAT(e.first_name," ",e.last_name) AS 姓名,
>         COUNT(ep.project_id) AS pro_num
>     	FROM employees e
>     	JOIN employee_projects ep
>     	ON e.emp_id = ep.emp_id
>     	GROUP BY e.emp_id
>     ),
>     num_list AS (
>     	SELECT pe.emp_id,pe.姓名,pe.pro_num,
>     	RANK() over(ORDER BY pe.pro_num DESC) AS num_all
>     	FROM pro_num_emp pe
>     )
>     SELECT *
>     FROM num_list
>     where num_all <= 1;
>     ```
>
> 
>
> 11. 计算所有员工的工资总和。
>
>     ```sql
>     SELECT SUM(e.salary) 工资总和
>     FROM employees e;
>     ```
>
> 
>
> 12. 查询姓"Smith"的员工信息。
>
>     ```sql
>     SELECT e.emp_id,
>     CONCAT(e.first_name," ",e.last_name) AS 姓名
>     FROM employees e
>     WHERE e.last_name = "Smith";
>     ```
>
> 
>
> 13. 查询即将在半年内到期的项目。
>
>     ```sql
>     SELECT p.project_id,p.project_name
>     FROM projects p
>     WHERE p.end_date < DATE_ADD(p.start_date, INTERVAL 6 MONTH);
>     
>     ```
>
> 
>
> 14. 查询至少参与了两个项目的员工。
>
>     ```sql
>     WITH join_proj AS (
>     	SELECT e.emp_id,
>     	CONCAT(e.first_name," ",e.last_name) AS 姓名,
>     	count(ep.project_id) AS proj_num
>     	FROM employees e
>     	JOIN employee_projects ep
>     	ON e.emp_id = ep.emp_id
>     	GROUP BY e.emp_id
>     )
>     SELECT jp.emp_id,jp.姓名,jp.proj_num
>     FROM join_proj jp
>     WHERE jp.proj_num >= 2;
>     ```
>
>     
>
> 15. 查询没有参与任何项目的员工。
>
>     ```sql
>     ```
>
>     
>
> 16. 计算每个项目参与的员工数量。
>
> 17. 查询工资第二高的员工信息。
>
> 18. 查询每个部门工资最高的员工。
>
> 19. 计算每个部门的工资总和,并按照工资总和降序排列。
>
> 20. 查询员工姓名、部门名称和工资。
>
> 21. 查询每个员工的上级主管(假设emp_id小的是上级)。
>
> 22. 查询所有员工的工作岗位,不要重复。
>
> 23. 查询平均工资最高的部门。
>
> 24. 查询工资高于其所在部门平均工资的员工。
>
> 25. 查询每个部门工资前两名的员工。
>
> 26. 查询跨部门的项目(参与员工来自不同部门)。
>
> 27. 查询每个员工的工作年限,并按工作年限降序排序。
>
> 28. 查询本月过生日的员工(假设hire_date是生日)。
>
> 29. 查询即将在90天内到期的项目和负责该项目的员工。
>
> 30. 计算每个项目的持续时间(天数)。
>
> 31. 查询没有进行中项目的部门。
>
> 32. 查询员工数量最多的部门。
>
> 33. 查询参与项目最多的部门。
>
> 34. 计算每个员工的薪资涨幅(假设每年涨5%)。
>
> 35. 查询入职时间最长的3名员工。
>
> 36. 查询名字和姓氏相同的员工。
>
> 37. 查询每个部门薪资最低的员工。
>
> 38. 查询哪些部门的平均工资高于公司的平均工资。
>
> 39. 查询姓名包含"son"的员工信息。
>
> 40. 查询所有员工的工资级别(可以自定义工资级别)。
>
> 41. 查询每个项目的完成进度(根据当前日期和项目的开始及结束日期)。
>
> 42. 查询每个经理(假设job_title包含'Manager'的都是经理)管理的员工数量。
>
> 43. 查询工作岗位名称里包含"Manager"但不在管理岗位(salary<70000)的员工。
>
> 44. 计算每个部门的男女比例(假设以名字首字母A-M为女性,N-Z为男性)。
>
> 45. 查询每个部门年龄最大和最小的员工(假设hire_date反应了年龄)。
>
> 46. 查询连续3天都有员工入职的日期。
>
> 47. 查询员工姓名和他参与的项目数量。
>
> 48. 查询每个部门工资最高的3名员工。
>
> 49. 计算每个员工的工资与其所在部门平均工资的差值。
>
> 50. 查询所有项目的信息,包括项目名称、负责人姓名(假设工资最高的为负责人)、开始日期和结束日期。