package vn.edu.likelion.assignment.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Member {
    private static final Scanner scanner = new Scanner(System.in);
    private int memberId;
    private String nameMember;
    private int age;
    private String joinDate;
    private String courseName;


    public Member(int memberId, String nameMember, int age, String courseName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.memberId = memberId;
        this.nameMember = nameMember;
        this.age = age;
        this.courseName = courseName;
        this.joinDate = LocalDateTime.now().format(formatter);
    }

    public int getMemberId() {
        return memberId;
    }

    public void edit() {
        // Set new name
        System.out.println("Enter new member name (Empty to skip): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            setNameMember(name);
        }

        // Set new course name
        System.out.println("Enter new course name (Empty to skip): ");
        String courseName = scanner.nextLine();
        if (!courseName.trim().isEmpty()) {
            setCourseName(courseName);
        }

        // Set new age
        System.out.println("Enter new age (0 to skip): ");
        int age = getUserChoice();
        if (age > 0) {
            setAge(age);
        }
        System.out.println("Chinh sua thanh cong !");
    }

    private int getUserChoice() {
        int choice = -1;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Nhập không hợp lệ. Vui lòng nhập số.");
            scanner.nextLine(); // Clear the invalid input
        }
        return choice;
    }

    public String getNameMember() {
        return nameMember;
    }

    public void setNameMember(String nameMember) {
        this.nameMember = nameMember;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
}
