package ir.mym.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mym on 1/27/17.
 */
public class Member{//agha mojtaba
    public int credit;
    public String firstName;
    public String lastName;
    public String identificationNo;//melli code
    public int clubID;
    public boolean monthly;//jalasei, mahi
    public boolean aerobiotic;
    public boolean boxer;
    public int birthYear;
    public String phoneNo;
    public String telegramId;
    public String imagePath;
    public boolean gender;//true = male, false= female
    public Date registerDate;
    public Date expireMembershipDate;
    public ArrayList<SportProgram> programs;

    public Member(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        registerDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(registerDate);
        cal.add(Calendar.DATE, 30); //minus number would decrement the days
        expireMembershipDate = cal.getTime();
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void increaseCredit(int offset){
        credit += offset;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }

    public boolean isMonthly() {
        return monthly;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }

    public boolean isAerobiotic() {
        return aerobiotic;
    }

    public void setAerobiotic(boolean aerobiotic) {
        this.aerobiotic = aerobiotic;
    }

    public boolean isBoxer() {
        return boxer;
    }

    public void setBoxer(boolean boxer) {
        this.boxer = boxer;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public Date getRegisterSQLDate() {
        return new java.sql.Date(registerDate.getTime());
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getExpireMembershipDate() {
        return expireMembershipDate;
    }

    public void setExpireMembershipDate(Date expireMembershipDate) {
        this.expireMembershipDate = expireMembershipDate;
    }

    public ArrayList<SportProgram> getPrograms() {
        return programs;
    }

    public void setPrograms(ArrayList<SportProgram> programs) {
        this.programs = programs;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public java.sql.Date getExpireMembershipSQLDate() {
        return new java.sql.Date(expireMembershipDate.getTime());
    }
}
