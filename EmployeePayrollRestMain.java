package Employee.Payroll_Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollRestMain {
	private List<Employee> empPayrollList;

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO;
	}

	public EmployeePayrollRestMain() {
	}

	public EmployeePayrollRestMain(List<Employee> employeeList) {
	
		this.empPayrollList = new ArrayList<>(employeeList); // Use new memory not the same as provided by client to
																// avoid confusion

	}

	public long countEntries(IOService ioService) {
		if (ioService.equals(IOService.REST_IO))
			return empPayrollList.size();
		return 0;
	}

	public void addEmployeeToPayroll(Employee employeeData, IOService ioService) {
		empPayrollList.add(employeeData);
	}
}
