package dating.app.fastdating.Presenter;

import dating.app.fastdating.Model.App;
import dating.app.fastdating.Model.AppDatabase;
import dating.app.fastdating.Model.Employee;
import dating.app.fastdating.Model.EmployeeDao;

import java.util.List;

public class Presenter {

    private AppDatabase db = App.getInstance().getDatabase();
    private EmployeeDao employeeDao = db.employeeDao();
    ;

    public void insert(Employee employee) {
        employeeDao.insert(employee);
    }

    public void update(Employee employee) {
//        Employee employee1
//        this.employee1 = employeeDao.getById(employee.id);
//        this.employee1.height = employee.height;
//        this.employee1.weight = employee.weight;
//        this.employee1.image = employee.image;

        employeeDao.update(employee);
    }


    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }

    public List<Employee> getAll() {
        List<Employee> employees = employeeDao.getAll();
        return employees;
    }

    public int getSize() {
        return employeeDao.getSize();
    }

    public Employee getById(long id) {
        return employeeDao.getById(id);
    }
    public Employee getByEmail(String email) {
        return employeeDao.getByEmail(email);
    }

    public boolean isUserExists(Employee newEmployee) {
        List<Employee> employees = employeeDao.getAll();

        for (int i = 0; i < employees.size(); i++) {
            Employee oldEmployee = employees.get(i);
            if (oldEmployee.email.equals(newEmployee.email)) {
                return true;
            }
        }
        return false;

    }

    public boolean addNewUser(Employee newEmployee) {
        int newId = getSize() + 1;
        newEmployee.id = newId;

        if (isUserExists(newEmployee)) {

            return false;
        } else {
            insert(newEmployee);
            return true;
        }
    }


    public boolean isLoginCredentialsCorrect(Employee user) {
        List<Employee> users = employeeDao.getAll();

        for (int i = 0; i < users.size(); i++) {
            Employee oldEmployee = users.get(i);

            String oldEmail = oldEmployee.email;
            String oldPassword = oldEmployee.password;

            String newEmail = user.email;
            String newPassword = user.password;

            if (oldEmail.equals(newEmail) && oldPassword.equals(newPassword)&&!newEmail.isEmpty()&&!newPassword.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    public void updateUserProfile(Employee newEmployee) {
        Employee oldEmployee = getByEmail(newEmployee.email);
        newEmployee.id=oldEmployee.id;
        update(newEmployee);
    }
}
