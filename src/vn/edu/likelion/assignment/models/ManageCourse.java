package vn.edu.likelion.assignment.models;

import vn.edu.likelion.assignment.interfaces.IManage;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class ManageCourse implements IManage {
    private static final Scanner scanner = new Scanner(System.in);
    private ArrayList<Course> courses;
    private int courseId = 0;

    public ManageCourse() {
        this.courses = new ArrayList<>();
    }

    public void add() {
        try {
            while (true) {
                if (getSize() >= 5) {
                    throw new IllegalArgumentException("Chỉ thêm tối đa 5 khóa học!");
                }
                System.out.println("-------------------------------");
                System.out.println("Bắt đầu thêm khóa học:");
                System.out.print("Nhập tên khóa học: ");

                // get name from keyboard and add to course
                String name = scanner.nextLine();
                Course course = new Course(courseId++, name);
                courses.add(course);

                System.out.println("Bạn có muốn tiếp tục: \n0. Quay lại\n1. Tiếp tục\n--------------------------");
                int choice = getUserChoice();
                if (choice == 0) {
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void edit() {

    }

    public void deleteCourseByID() {
        try {
            if (courses.isEmpty()) {
                throw new ConcurrentModificationException("Không có khóa học nào!");
            }
            System.out.println("-------------------------------------");
            System.out.println("Nhập ID khóa học cần xóa: ");
            // get course id from user
            int courseId = getUserChoice();

            // get course by course id to delete
            Course courseToRemove = courses.stream()
                    .filter(course -> course.getCourseId() == courseId)
                    .findFirst()
                    .orElse(null);

            // if the course not exist throw new exception
            if (courseToRemove == null) {
                throw new ConcurrentModificationException("Không tìm thấy khóa học!");
            }

            // else delete it
            courses.remove(courseToRemove);
            System.out.println("Xóa thành công!");
        } catch (ConcurrentModificationException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteCourse(Course course) {
        courses.remove(course);
    }

    public void detailCoursesCurrent() {
        if (courses.isEmpty()) {
            throw new IllegalArgumentException("Không có khóa học!");
        }
        System.out.println("----------------------------------");
        System.out.println("Các khóa học hiện có: ");

        int stt = 1; // Used to make the object's serial number

        for (Course course : courses) {
            System.out.print("STT: " + (stt++)
                    + " - ID: " + course.getCourseId()
                    + " - Tên khóa học: " + course.getNameCourse()
                    + " - Tổng số thành viên: " + course.getSize()
                    + " - Trạng thái: " + course.getStringStatusCourse()
                    + "\n");
            System.out.println("----------------------------------");
        }
    }

    public void showAllMembers() {
        if (isEmptyMemberList()) {
            throw new IllegalArgumentException("Không có thành viên nào");
        }

        // get sorted courses
        ArrayList<Course> sortedCourses = getSortedCourses();
        for (Course course : sortedCourses) {
            course.detailMemebersCurrent();
        }
    }

    private ArrayList<Course> getSortedCourses() {
        //object initialization sorted from courses
        ArrayList<Course> sortedCourses = new ArrayList<>(courses);
        Course temp;
        for (int i = 0; i < sortedCourses.size() - 1; i++) {
            for (int j = i + 1; j < sortedCourses.size(); j++) {
                temp = sortedCourses.get(i);
                sortedCourses.set(i, sortedCourses.get(j));
                sortedCourses.set(j, temp);
            }
        }
        return sortedCourses;
    }

    public boolean isEmptyMemberList() {
        for (Course course : courses) {
            if (!course.members.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Course getCourse(int courseId) {
        return courses.stream().filter(course -> course.getCourseId() == courseId).findFirst().orElse(null);
    }

    @Override
    public int getSize() {
        return this.courses.size();
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
}
