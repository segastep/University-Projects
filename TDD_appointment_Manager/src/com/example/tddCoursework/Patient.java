package com.example.tddCoursework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class Patient
 * 
 * @author faceless
 * @Usage - Creates object of type person, provides unique id to each instance
 *        of the object takes list of type Appointment and stores appointments
 *        in a list
 * @override - toString() - made to match our needs
 * 
 *
 */
public class Patient {
	private String name;
	private String surname;
	private String adress;
	private String dateOfBirth;

	private String phoneNumber;
	private int id;

	static AtomicInteger nextId = new AtomicInteger();
	private List<Appointment> appointments;

	/**
	 * @constructor -
	 * 
	 * @param name
	 * @param surname
	 * @param adress
	 * @param dateOfBirth
	 * @param phoneNumber
	 */
	public Patient(String name, String surname, String adress, String dateOfBirth, String phoneNumber) {
		this.name = name;
		this.surname = surname;
		this.adress = adress;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		setId(nextId.incrementAndGet());
		appointments = new ArrayList<Appointment>();
	}

	/**
	 * @purpose - function to add new appointment to list of type Appointment
	 */
	public void addAppointment(String date, String notes) {
		appointments.add(new Appointment(date, notes));
	}

	public List<Appointment> getAppointment() {
		return appointments;
	}

	/**
	 * Makes array string representation of all appointment used in @toString()
	 * 
	 * @return - printAppointment = Array string representation of all
	 *         Appointment objects
	 */
	public String printAppointments() {
		String printAppointment = Arrays.toString(appointments.toArray());
		return printAppointment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAddress(String adress) {
		this.adress = adress;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getAdress() {
		return adress;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public String toString() {
		String printMe = "\n***************************************" + "\n" + "Person: " + name + " " + surname
				+ "\n***************************************" + "\nDate of Birth: " + dateOfBirth + "\nAdress: "
				+ adress + "\nPhone Number: " + phoneNumber + "\nPatient's ID: " + id + "\nAppointmets History "
				+ printAppointments();

		return printMe;

	}

}
