package com.example.tddCoursework;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ActualLogginTest extends AbstractLoggingJUnitTest {
	/** Class Appointment Testing methods ***/
	/**
	 * @constructor - 2 params - date notes;
	 * @getters - Date, Notes
	 * @override - toString()
	 */
	private String testDate = "22/10/2013";
	private String testNotes = "testNote";
	private List<Appointment> testAdd;
	private Appointment appointment;

	@Before
	public void initialiseAppointment() {
		appointment = new Appointment(testDate, testNotes);

	}

	@Test
	public void testAppointmentGetDate() {
		appointment.getDate();
		assertTrue("22/10/2013".equals(appointment.getDate()));
	}

	@Test
	public void testAppointmentGetNote() {
		appointment.getNotes();
		assertTrue("testNote".equals(appointment.getNotes()));
	}

	@Test

	public void testAppointmentToString() {
		appointment.toString();
		assertEquals("\nDate: " + "22/10/2013" + "\nNotes taken: " + "testNote", appointment.toString());

	}
	
	/*********Class Patient Test Methods**************/

	private String name="Steven";
	private String surname="Jones";
	private String address ="174 Sandyford Road";
	private String dateOfBirth = "07/08/1993";
	private String phoneNumber= "07842652111";
	private static Patient testPatient;
	
@Before
public void testPatientClassConstructor(){
	testPatient = new Patient(name, surname, address, dateOfBirth, phoneNumber);
	;
}

@Test

public void testPatientSetId(){
	 
	testPatient.setId(1);
	assertTrue(testPatient.getId()==1);
}

@Test
public void testPatientGetId(){
	testPatient.setId(2);
	assertTrue(testPatient.getId()==2);
	
}

@Test
public void testAddAppointment(){
	List<Appointment> expectedList = new ArrayList<Appointment>();
	expectedList.add(new Appointment("22/10/2013", "testNote"));
	testAdd = new ArrayList<Appointment>();
	
	testPatient.addAppointment(testDate, testNotes);
	testPatient.getAppointment();
	assertEquals(expectedList.get(0).getNotes().toString(),testPatient.getAppointment().get(0).getNotes().toString());	
	
}
@Test
public void testprintAppointments(){
	
	testPatient.addAppointment(testDate, testNotes);
	assertEquals("[\n"+ "Date: 22/10/2013" + "\n" + "Notes taken: testNote]", testPatient.printAppointments());
	
	/**Took me many attempts before I managed to get this working I tried
	 * using appointments.toString() as it returns exactly the same 
	 * representation, but thus in the method addAppointment we actually 
	 * represent the notes as array of notes extra pair of brackets is 
	 * added and this fails the method, I tried many different approaches 
	 * and searched in the web for solutions but I find none. I would
	 * be very thankful if in the feedback You could explain me how to 
	 * test this in a sophisticated way, thanks !
	 */
}



@Test
public  void testToString(){
	Patient testSteven = new Patient("Steven", "Shore", "Monday Crescent 11", "07/10/1989", "01901452365");
	testSteven.addAppointment("23/10/2013", "Note");
	assertTrue(testSteven.toString().contains("Steven"));
	assertTrue(testSteven.toString().contains("23/10/2013"));
		
}
/******Test for Manager class******************/
private static List<Patient> testPatients;
private Manager testManager;

@Before
public void initialise(){
	
	testPatients = new ArrayList<Patient>();
	testManager = new Manager();
}

@Test 
public  void testAddNewPatient(){
	testManager.addNewPatient("Nicole", "McKeey", "Bell Avenue 112", "01/01/1990", "+447842116888");
	assertTrue(Manager.patients.get(0).toString().contains("Nicole"));	
}

	@Test
	public void testSearchByName() {

		Manager.patients.clear();
		testManager.addNewPatient("Nicole", "Bell", "Bell Avenue 112", "01/01/1990", "+447842116888");
		testManager.addNewPatient("False", "McKeey", "Bell Avenue 112", "01/01/1990", "+447842116888");
		Manager.searchByName("False");
		assertNotEquals(Manager.patients.get(1).getName().toString(), Manager.patients.get(0).getName().toString());

		testManager.addNewPatient("Nicole", "Bell", "Bell Avenue 112", "01/01/1990", "+447842116888");
		testManager.addNewPatient("Nicole", "McKeey", "Bell Avenue 112", "01/01/1990", "+447842116888");
		Manager.searchByName("False");
		assertEquals(Manager.patients.get(2).getName().toString(), Manager.patients.get(3).getName().toString());

		testManager.addNewPatient("Nicole", "Bell", "Bell Avenue 112", "01/01/1990", "+447842116888");
		testManager.addNewPatient("Nicole", "McKeey", "Bell Avenue 112", "01/01/1990", "+447842116888");
		Manager.searchByName("Nicole");
		assertEquals(Manager.patients.get(3).getName().toString(), Manager.patients.get(4).getName().toString());

	}
	
	@Test
	public void testChangeNameAdress(){
		Manager.patients.clear();
		String expected = "Steven" + "Shore" + "newAddress";
		testManager.addNewPatient("Nicole", "Bell", "Bell Avenue 112", "01/01/1990", "+447842116888");
		Manager.changeNameAdress(Manager.patients.get(0).getId(), "Steven", "Shore", "newAddress");
		String actualName = Manager.patients.get(0).getName().toString();
		String actualSurname = Manager.patients.get(0).getSurname().toString();
		String actualAddress = Manager.patients.get(0).getAdress().toString();
		String actual = actualName + actualSurname + actualAddress;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMakeNewAppointment(){
		
		String expectedResult = "[\n"+ "Date: 04/10/2015" + "\n" + "Notes taken: Awesome Note]";
		Manager.makeNewAppointment(Manager.patients.get(0).getId(), "04/10/2015", "Awesome Note");
		String actualResult = Manager.patients.get(0).getAppointment().toString();
		assertEquals(expectedResult, actualResult);
	}

}


