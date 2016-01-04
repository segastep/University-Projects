package com.example.tddCoursework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Manager {
	public int exit;
	public static List<Patient> patients = new ArrayList<Patient>();
	public static List<Patient> dublicates = new ArrayList<Patient>();

	public void addNewPatient(String name, String surname, String adress, String dateOfBirth, String phoneNumber) {
		patients.add(new Patient(name, surname, adress, dateOfBirth, phoneNumber));

	}

	/**
	 * @return printAllPatients - String representation of List Patients which
	 *         is of type Person
	 * 
	 */
	public static String printPatients() {

		String printAllPatients = Arrays.toString(patients.toArray());
		return printAllPatients;

	}

	/**
	 * 
	 * @param searchName
	 *            - name to search
	 * @return all found dublicates stored in a list
	 * @purpose - To find all patients with identical names Loops around all
	 *          patients searching for identical name
	 */
	public static List<Patient> searchByName(String searchName) {

		for (int a = 0; a <= patients.size() - 1; a++) {

			if (patients.get(a).getName().equals(searchName)) {
				dublicates.add(patients.get(a));
			}
		}
		if (dublicates.size() - 1 >= 1) {
			System.out.println("Following duplicate names found : \n" + Arrays.toString(dublicates.toArray()));
		} else
			System.out.println("No duplicates were found during search !");
		return dublicates;
	}

	/**
	 * @params
	 * 
	 * @param idToCheck
	 *            - identify user by id
	 * @param newName
	 *            - Desired new name
	 * @param newSurname
	 *            - Desired new surname
	 * @param newAdress
	 *            - Desired new address goes through all patient objects stored
	 *            in a list untill it finds the correspoing ID once found
	 *            changes the name surname and adress of the Person object with
	 *            the matching ID
	 */
	public static void changeNameAdress(int idToCheck, String newName, String newSurname, String newAdress) {
		int i;
		for (i = 0; i <= patients.size() - 1; i++) {
			if (patients.get(i).getId() == idToCheck) {

				patients.get(i).setName(newName);
				patients.get(i).setSurname(newSurname);
				patients.get(i).setAddress(newAdress);

			} else
				continue;
		}

	}

	/**
	 * @params
	 * 
	 * @param id
	 *            - Id of the user to make an appointment with
	 * @param date
	 *            - Set date of the appointment
	 * @param notes
	 *            - Add any notes
	 * @purpose Create new appointment instance for instance of a Patient object
	 *          Finds the Patient with the coresponding ID and adds new
	 *          Appointment object to the list of appointments corresponding to
	 *          that Patient object
	 */
	public static void makeNewAppointment(int id, String date, String notes) {
		int c;
		for (c = 0; c <= patients.size() - 1; c++) {
			if (id == patients.get(c).getId()) {
				patients.get(c).addAppointment(date, notes);
			}
		}
	}

	/** Used for console to help explain options available */
	public static String options() {
		String optionsList = "\nTo add Patient please type 1\n" + "To print a list of all patients please press 2\n"
				+ "To change the name and address of a Patient press 3\n" + "To search Patient by name press 4\n"
				+ "To add appointment press 5\n" + "To exit press 6";
		return optionsList;
	}

	/** Simple prompt appearing at the start of the program */
	public static String prompt() {
		String prompt = "Welcome to this test Appointment manager\n" + "Choose one of the following options: \n"
				+ options();
		return prompt;
	}

	/** Manageing options, based on input from user */
	public void optionsExecution() {

		Scanner sc = new Scanner(System.in);
		int option = sc.nextInt();
		if (option == 1) {

			System.out.println(
					"Please enter name, surname, adress, date of birth and phone number - use strings only, press enter after each input");
			String[] input = new String[6];
			for (int i = 0; i <= input.length - 1; i++) {
				input[i] = sc.nextLine();
			}

			addNewPatient(input[1], input[2], input[3], input[4], input[5]);
			System.out.println("New Patient Added !");
			System.out.println("What do you want to do next ? \n");
			return;
		}

		if (option == 2) {

			System.out.println(printPatients());
			System.out.println("What do you want to do next ? \n");

			return;
		}

		if (option == 3) {

			System.out.println(
					"Please choose which patient to rename by chosing their id and typeing the new name and address\n");
			int id = sc.nextInt();
			String[] input = new String[4];
			for (int i = 0; i <= input.length - 1; i++) {
				input[i] = sc.nextLine();
			}

			changeNameAdress(id, input[1], input[2], input[3]);
			System.out.println("Patient Renamed and address changed");
			System.out.println("What do you want to do next ? \n");
			return;

		}
		if (option == 4) {

			String[] searchName = new String[2];
			searchName[0] = sc.nextLine();
			searchName[1] = sc.nextLine();

			searchByName(searchName[1]);
			System.out.println("What do you want to do next ? \n");
			return;

		}
		if (option == 5) {
			System.out.println(
					"To add new appointment please Enter the Patient ID and new meeting will be added to the corresponding Patient, than enter date and notes\n");
			int index = sc.nextInt();
			String[] input = new String[3];
			for (int i = 0; i <= input.length - 1; i++) {
				input[i] = sc.nextLine();
			}

			makeNewAppointment(index, input[1], input[2]);
			System.out.println("Appointment added !");
			System.out.println("What do you want to do next ? ");
			return;
		}
		if (option == 6)
			sc.close();
		System.out.println("Program Finished !");
		System.exit(1);

	}

	/** Creates new instance of Manager object, executes the programs code */
	public static void main(String[] args) {
		Manager surgeryOne = new Manager();
		System.out.println(prompt());
		System.out.println("Please choose what you want to do: \n");
		do {
			surgeryOne.optionsExecution();
		} while (true);
	}
}
