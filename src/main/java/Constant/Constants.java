package Constant;

import Model.Semester;
import Model.Student;

public class Constants {
    public static Student authStudent=null;
    public static Semester upcomingSemester=null;
    public static Student enrollmentStudent=null;
    // Image Paths
    public static class ImagePaths {
        public static final String STUDENT = "student_images/";
        public static final String TEACHER = "teachers_images/";
        public static final String STUDENT_FOLDER = "/student_images/";
        public static final String TEACHER_FOLDER = "/teachers_images/";
    }

    // Alert Messages
    public static class Alerts {
        public static final String UPDATE_SUCCESS = "Successfully updated";
        public static final String SAVE_SUCCESS = "Successfully Saved!!";
        public static final String ERROR = "ERROR";
        public static final String INFO = "INFORMATION";
        public static final String DELETE_CONFIRM_TITLE = "Delete Confirmation";
        public static final String DELETE_CONFIRM_MESSAGE = "Are you sure to delete record?\n";
        public static final String DUPLICATE_RECORD = "Duplicate Record found!!! ";
        public static final String STUDENT_NOT_FOUND = "Student not found. Try again";
        public static final String INCORRECT_PASSWORD = "Incorrect password. Try again.";
        public static final String NO_SELECTED = "Row does not Selected";
    }

    // Common Selections
    public static class Selections {
        public static final String SELECT_ITEM = "--Please Select one item--";
    }

    // Field Constraints
    public static class FieldConstraints {
        public static final String ID = "id";
        public static final String MAX_CAPACITY = "The maximum capacity is 100";
        public static final String MAX_CREDITS = "Credit cannot be more than 20";
        public static final String NEW_STATUS = "New";
        public static final String REGISTER_STATUS = "Registered";
        public static final int  CREDITS_LIMIT= 20;
    }

    // View Paths
    public static class Views {
        public static final String STUDENT = "student-management.fxml";
        public static final String TEACHER = "teacher-management.fxml";
        public static final String FACULTY = "faculty-management.fxml";
        public static final String DEGREE = "degree-management.fxml";
        public static final String DEPARTMENT = "department-management.fxml";
        public static final String COURSE = "course-management.fxml";
        public static final String LOGIN = "login.fxml";
        public static final String ENROLLMENT = "/org/example/mylearningproject/view/enrollments.fxml";
        public static final String ENROLLMENT_LOGIN_VIEW = "enrollments-login.fxml";
        public static final String MAIN_VIEW = "/org/example/mylearningproject/main-view.fxml";
        public static final String SEMESTER = "semester.fxml";

    }
}
