package vn.edu.likelion.assignment.models;

import vn.edu.likelion.assignment.interfaces.IManage;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Course implements IManage {

    private static final Scanner sc = new Scanner(System.in);
    private boolean status;
    private int courseId;
    private String nameCourse;
    private int memberId = 0;

    ArrayList<Member> members;

    public Course(int courseId, String nameCourse) {
        this.status = false;
        this.courseId = courseId;
        this.nameCourse = nameCourse;
        members = new ArrayList<>();
    }

    @Override
    public void add() {
        try {
            if (getSize() == 3) {
                throw new IllegalArgumentException("Chi them duoc toi da 3 thanh vien !");
            }
            System.out.println("Dang them thanh vien cho khoa hoc: " + getNameCourse());
            System.out.print("Enter name member: ");
            String name = sc.nextLine();

            System.out.print("Enter age member: ");
            int age = getUserChoice();
            while (age < 0) {
               age = getUserChoice();
            }
            sc.nextLine();
            String nameCourse = getNameCourse();
            Member newMember = new Member(memberId++, name, age, nameCourse);

            members.add(newMember);
            status = members.size() == 3;
            System.out.println("Da them thanh cong !");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void edit() {
        System.out.println("Enter new course name: ");
        String courseName = sc.nextLine();

        setNameCourse(courseName);

        for (Member m: members) {
            // Reassign the course object to all members after the change
            m.setCourseName(courseName);
        }
        System.out.println("Edit success !");
    }

    private static int getUserChoice() {
        int choice = -1;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Khong hop le ! Vui long chon lai.");
            sc.nextLine(); // Clear the invalid input
        }
        return choice;
    }


    public void removeMember(int memberId) {
        try {
            // Get member if exist
            members.stream()
                    .filter(member -> member.getMemberId() == memberId)
                    .findFirst().ifPresent(existMember -> members.remove(existMember));
            // Change status course
            status = !members.isEmpty();
            System.out.println("Xoa thanh cong !");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return  " Status: " + getStringStatusCourse() +
                " ID: " + courseId +
                " Name course: " + nameCourse +
                " Total member: " + getSize();
    }

    public void detailMemebersCurrent () throws IllegalArgumentException {
        System.out.println("---------" + getNameCourse() + "--------");
        System.out.print("Cac thanh vien trong khoa hoc: \n");

        if (getSize() == 0) {
            System.out.println("(No member)");
        } else {
            System.out.println();
        }
        int stt=1;
        for (Member m: members) {
            System.out.println("STT : " + (stt++)
                    + " - ID: " + m.getMemberId()
                    + " - Full Name: " + m.getNameMember()
                    + " - Age: " + m.getAge()
                    + " Join Date: " + m.getJoinDate()
                    + " - Course: " + m.getCourseName()
            );

        }
    }


    public String getStringStatusCourse() {
        return status ? "Ready" : "Not Ready";
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }



    @Override
    public int getSize() {
        return members.size();
    }

    public Member getMembersByID(int memberID) {
        return members.stream().filter(member -> member.getMemberId() == memberID).findFirst().orElse(null);
    }
}
