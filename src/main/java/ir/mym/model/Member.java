package ir.mym.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mym on 1/27/17.
 */
public class Member{//agha mojtaba
    public int credit;
    public String firstname;
    public String lastname;
    public String identificationNo;//melli code
    public int takhtiID;
    public String membershipType;//jalasei, mahi
    public boolean aerobiotic;
    public int birthYear;
    public String phoneNo;
    public String telegramID;
    public String picturePath;
    public ArrayList<Date> mebershipDate;
    public ArrayList<SportProgram> programs;

    public Member(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public int getTakhtiID() {
        return takhtiID;
    }

    public void setTakhtiID(int takhtiID) {
        this.takhtiID = takhtiID;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public boolean isAerobiotic() {
        return aerobiotic;
    }

    public void setAerobiotic(boolean aerobiotic) {
        this.aerobiotic = aerobiotic;
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

    public String getTelegramID() {
        return telegramID;
    }

    public void setTelegramID(String telegramID) {
        this.telegramID = telegramID;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setImagePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public ArrayList<Date> getMebershipDate() {
        return mebershipDate;
    }

    public void setMebershipDate(ArrayList<Date> mebershipDate) {
        this.mebershipDate = mebershipDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "credit=" + credit +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", identificationNo='" + identificationNo + '\'' +
                ", takhtiID=" + takhtiID +
                ", membershipType='" + membershipType + '\'' +
                ", aerobiotic=" + aerobiotic +
                ", birthYear=" + birthYear +
                ", phoneNo='" + phoneNo + '\'' +
                ", telegramID='" + telegramID + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", mebershipDate=" + mebershipDate +
                '}';
    }
}
