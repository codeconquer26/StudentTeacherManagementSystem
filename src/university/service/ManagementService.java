package university.service;

import university.model.*;
import java.util.ArrayList;
import java.util.List;

public class ManagementService {
    private final ArrayList<Student> students = new ArrayList<>();
    private final ArrayList<Teacher> teachers = new ArrayList<>();
    private final ArrayList<Course> courses = new ArrayList<>();

    public List<Student> getStudents() { return students; }
    public List<Teacher> getTeachers() { return teachers; }
    public List<Course> getCourses() { return courses; }

    public boolean studentIdExists(int id) {
        for (Student s : students) if (s.getId() == id) return true;
        return false;
    }

    public boolean teacherIdExists(int id) {
        for (Teacher t : teachers) if (t.getId() == id) return true;
        return false;
    }

    public boolean courseIdExists(int id) {
        for (Course c : courses) if (c.getId() == id) return true;
        return false;
    }

    public boolean addStudent(int id, String name, String department) {
        if (studentIdExists(id)) return false;
        students.add(new Student(id, name, department));
        return true;
    }

    public boolean addTeacher(int id, String name, String subject) {
        if (teacherIdExists(id)) return false;
        teachers.add(new Teacher(id, name, subject));
        return true;
    }

    public boolean addCourse(int id, String name, String teacherName) {
        if (courseIdExists(id)) return false;
        courses.add(new Course(id, name, teacherName));
        return true;
    }

    public Student findStudent(int id) {
        for (Student s : students) if (s.getId() == id) return s;
        return null;
    }

    public Teacher findTeacher(int id) {
        for (Teacher t : teachers) if (t.getId() == id) return t;
        return null;
    }

    public Course findCourse(int id) {
        for (Course c : courses) if (c.getId() == id) return c;
        return null;
    }

    public boolean deleteStudent(int id) {
        Student s = findStudent(id);
        return s != null && students.remove(s);
    }

    public boolean deleteTeacher(int id) {
        Teacher t = findTeacher(id);
        return t != null && teachers.remove(t);
    }

    public boolean deleteCourse(int id) {
        Course c = findCourse(id);
        return c != null && courses.remove(c);
    }
}
