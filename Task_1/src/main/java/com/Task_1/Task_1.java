import java.util.ArrayList;
import java.lang.Math;
import java.time.*;
import java.time.LocalDateTime; 



public class Task_1{

    public static void main(String[] str){

        Developer dev = new Developer("Maks", "Vovk", 1);
        dev.setSalary(4000);
        dev.setNumbHoursWork(170);
        Developer dev2 = new Developer("Maks", "Vovk", 2);
        dev2.setSalary(4000);
        dev2.setNumbHoursWork(300);
        ArrayList<Employee> employees = new ArrayList();
        employees.add(dev);
        employees.add(dev2);
        Counter counter = new Counter(employees, 11, 2020);
        System.out.println(counter.getAllSalary());
        System.out.println("All salary<br> -_- % -_- <br>(");
        System.out.println(dev.getPercentTimeWork(11, 2020));
        System.out.println(dev2.getPercentTimeWork(11, 2020));
    }

}


abstract class Employee
{
    protected int id;
    protected String firstName;
    protected String lastName;
    protected double salary;
    protected double numbHoursWork;
    protected double hoursInWeekday = 8.0;

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public int getId()
    {
        return id;
    }

    public abstract double getMonthlySalary(int month, int year);

    public void setSalary(int sal)
    {
        salary = sal;
    }

    public void setNumbHoursWork(int hours)
    {
        numbHoursWork = hours;
    }

    public double getNumbHoursWorkInMonth(int month, int year){
        return getWeekdays(month, year) * hoursInWeekday;
    }

    public int getPercentTimeWork(int month, int year)
    {
        return (int)Math.round(100 * numbHoursWork / (hoursInWeekday * getWeekdays(month, year)));
    }

    protected double getSalaryPerHour(int month, int year)
    {
        return (int)Math.round(salary / (getWeekdays(month, year) * hoursInWeekday));
    }

    protected double getWeekdays(int month, int year)
    {
        int lastday = YearMonth.of(year, month).lengthOfMonth();
        double weekday = 0.0;
        double weekdays = 0.0;
        for (int day = 29; day <= lastday; day++) {
            weekday = LocalDate.of(year, month, day).getDayOfWeek().ordinal();
            if (weekday > 0 && weekday < 6) 
                weekdays++;
        }
        weekdays += 20.0;
        return weekdays;
    }
}


class Developer extends Employee
{
     
    public Developer(String cFirstName, String cLastName, int cId)
    {
        firstName = cFirstName;
        lastName = cLastName;
        id = cId;
        hoursInWeekday = 8.0;
        
    }

    public double getMonthlySalary(int month, int year){
        return getSalaryPerHour(month, year) * numbHoursWork;
    }

}


class Manager extends Employee
{
    
    public Manager(String cFirstName, String cLastName, int cId)
    {
        firstName = cFirstName;
        lastName = cLastName;
        id = cId;
        hoursInWeekday = 8.0;
    }


    public double getMonthlySalary(int month, int year)
    {
        if (getPercentTimeWork(month, year) > 100)
            return salary;
        return getSalaryPerHour(month, year) * numbHoursWork;
    }
}


class Counter
{
    private ArrayList<Employee> employees;
    private int month;
    private int year;

    public Counter(ArrayList<Employee> cEmployees, int cMonth, int cYear)
    {
        month = cMonth;
        year = cYear;
        employees = cEmployees;
    }

    public int getAllSalary()
    {
        int sumSalary = 0;
        for (Employee employee: employees) {
            sumSalary += employee.getMonthlySalary(month, year);
        }
        return sumSalary;
    }
}