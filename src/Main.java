import vn.edu.likelion.assignment.models.Course;
import vn.edu.likelion.assignment.models.ManageCourse;
import vn.edu.likelion.assignment.models.Member;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ManageCourse manageCourse = new ManageCourse();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    manageCourse.add();
                    break;
                case 2:
                    deleteCourse();
                    break;
                case 3:
                    viewCourses();
                    break;
                case 4:
                    showAllMembers();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    break;
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("----------------------------------");
        System.out.println("Vui long chon chuc nang:");
        System.out.println("1. Them khoa hoc");
        System.out.println("2. Xoa khoa hoc");
        System.out.println("3. Xem khoa hoc");
        System.out.println("4. Xem tat ca thanh vien !");
        System.out.println("0. Thoat");
        System.out.println("----------------------------------");
    }

    private static int getUserChoice() {
        int choice = -1;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            System.out.println("Khong hop le ! Vui long chon lai.");
            scanner.nextLine(); // Clear the invalid input
        }
        return choice;
    }

    private static void deleteCourse() {
        try {
            // Show list courses to pick ID courses before delete
            manageCourse.detailCoursesCurrent();

            // Begin delete
            manageCourse.deleteCourseByID();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void viewCourses() {
        try {
            while (true) {
                // Show list courses to pick ID courses
                manageCourse.detailCoursesCurrent();

                System.out.println("0. Quay lai\n1. Xem chi tiet khoa hoc");
                int choice = getUserChoice();

                if (choice == 0) {
                    break;
                } else if (choice == 1) {
                    menuDetailCourse();
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void showAllMembers() {
        try {
            manageCourse.showAllMembers();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void menuMember(Course course, Member member) {
        System.out.println("----------------------");
        System.out.println("Ban dang chon thanh vien (" + member.getNameMember() + "):");
        System.out.println("0. Quay lai");
        System.out.println("1. Chinh sua");
        System.out.println("2. Xoa");
        int choice = getUserChoice();

        switch (choice) {
            case 0:
                break;
            case 1:
                member.edit();
                break;
            case 2:
                course.removeMember(member.getMemberId());
                break;
            default:
                System.out.println("Invalid choice, please try again.");
                break;
        }
    }

    public static void menuDetailCourse() {
        System.out.println("Nhap ID khoa hoc: ");
        int courseNumber = getUserChoice();

        Course course = manageCourse.getCourse(courseNumber);
        if (course == null) {
            System.err.println("Course not found");
            return;
        }
        System.out.println("----------------------");
        System.out.println("Ban dang chon khoa hoc (" + course.getNameCourse() + "):");

        while (true) {
            System.out.println("0. Quay lai");
            System.out.println("1. Xoa");
            System.out.println("2. Them thanh vien");
            System.out.println("3. Xem thanh vien");
            System.out.println("4. Xoa thanh vien");
            System.out.println("5. Sua khoa hoc");
            System.out.println("----------------------");
            System.out.print("Choose an option: ");
            int choice = getUserChoice();

            switch (choice) {
                case 0:
                    return;
                case 1:
                    manageCourse.deleteCourse(course);
                    return;
                case 2:
                    course.add();
                    break;
                case 3:
                    viewMembers(course);
                    break;
                case 4:
                    removeMember(course);
                    break;
                case 5:
                    course.edit();
                    break;
                default:
                    System.out.println("Khong hop le ! Vui long nhap lai.");
                    break;
            }
        }
    }

    private static void viewMembers(Course course) {
        try {
            course.detailMemebersCurrent();
            System.out.println("0. Quay lai\n1. Xem chi tiet thanh vien");
            int choice = getUserChoice();

            if (choice == 0) {
                return;
            } else if (choice == 1) {
                System.out.println("Nhap ID thanh vien: ");
                int memberId = getUserChoice();
                Member member = course.getMembersByID(memberId);
                menuMember(course, member);
            } else {
                System.out.println("Khong hop le ! Vui long nhap lai.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void removeMember(Course course) {
        course.detailMemebersCurrent();
        System.out.println("Nhap ID thanh vien de xoa: ");
        int memberId = getUserChoice();
        course.removeMember(memberId);
    }
}
