package Employee.Payroll_Employee;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Employee.Payroll_Employee.EmployeePayrollRestMain.IOService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class PayrollTest {
	EmployeePayrollRestMain empPayrollService = null;
	private static Logger log = Logger.getLogger(PayrollTest.class.getName());

	@Before
	public void setUp() {
		empPayrollService = new EmployeePayrollRestMain();
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	public Employee[] getEmployeeList() {
		Response response = RestAssured.get("/employees");
		log.info("Employee payroll entries in JSON Server :\n" + response.asString());
		Employee[] arrayOfEmps = new Gson().fromJson(response.asString(), Employee[].class);
		return arrayOfEmps;
	}

	private Response addEmployeeToJSONServer(Employee employeeData) {
		String empJson = new Gson().toJson(employeeData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		return request.post("/employees");
	}
	
	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldMatch() {
		Employee[] arrayOfEmps = getEmployeeList();
		empPayrollService = new EmployeePayrollRestMain(Arrays.asList(arrayOfEmps));
		
		empPayrollService.updateEmployeeSalary("Verma", 4000000.00, IOService.REST_IO);
		
		Employee employeeData = empPayrollService.getEmployeeData("Mark");
		String empJson = new Gson().toJson(employeeData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		Response response = request.put("/employees/" + employeeData.id);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
	}
	
}
