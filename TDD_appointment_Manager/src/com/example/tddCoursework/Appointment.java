package com.example.tddCoursework;


/************************Class Appointment*****************************/
/**************@params - notes, date @type String***********************/
/**************@purpose Create object of type Appointment**************/
/**************@usage instances of it will be used in Patient class***/
/**************@toString() - override toString method to return******/
/***************************needed string representation of fields***/
public class Appointment {
	private String notes;
	private String print;
	private String date;

	public Appointment(String date, String notes) {
		this.notes = notes;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public String getNotes() {
		return notes;
	}
	
	@Override
	public String toString() {
		print = "\nDate: " + date + "\nNotes taken: " + notes;
		return print;
	}
}

