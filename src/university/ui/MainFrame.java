package university.ui;

import university.model.*;
import university.service.ManagementService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final ManagementService service;

    public MainFrame(ManagementService service) {
        this.service = service;
        setTitle("Teacher And Student Management System");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10));

        JLabel title = new JLabel("Teacher And Student Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JButton addStudent = new JButton("1. Add Student");
        JButton showStudent = new JButton("2. Show Students");
        JButton addTeacher = new JButton("3. Add Teacher");
        JButton showTeacher = new JButton("4. Show Teachers");
        JButton addCourse = new JButton("5. Add Course");
        JButton showCourse = new JButton("6. Show Courses");
        JButton search = new JButton("7. Search");
        JButton exit = new JButton("8. Exit");

        add(addStudent); add(showStudent); add(addTeacher); add(showTeacher);
        add(addCourse); add(showCourse); add(search); add(exit);

        addStudent.addActionListener(e -> addStudentDialog());
        showStudent.addActionListener(e -> showStudents());
        addTeacher.addActionListener(e -> addTeacherDialog());
        showTeacher.addActionListener(e -> showTeachers());
        addCourse.addActionListener(e -> addCourseDialog());
        showCourse.addActionListener(e -> showCourses());
        search.addActionListener(e -> searchDialog());
        exit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private Integer readId(Component parent, String message) {
        String text = JOptionPane.showInputDialog(parent, message);
        if (text == null) return null;
        try { return Integer.parseInt(text); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent, "Please enter a valid ID!");
            return null;
        }
    }

    private void addStudentDialog() {
        JTextField id = new JTextField(), name = new JTextField(), dept = new JTextField();
        JPanel p = new JPanel(new GridLayout(3,2));
        p.add(new JLabel("Student ID:")); p.add(id);
        p.add(new JLabel("Student Name:")); p.add(name);
        p.add(new JLabel("Department:")); p.add(dept);
        if (JOptionPane.showConfirmDialog(this,p,"Add Student",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            try {
                int studentId=Integer.parseInt(id.getText());
                if (!service.addStudent(studentId,name.getText(),dept.getText()))
                    JOptionPane.showMessageDialog(this,"Student ID already exists!");
                else JOptionPane.showMessageDialog(this,"Student added successfully!");
            } catch(NumberFormatException ex) { JOptionPane.showMessageDialog(this,"Please enter a valid ID!"); }
        }
    }

    private void showStudents() {
        JTextArea out=new JTextArea(); out.setEditable(false); out.setFont(new Font("Arial",Font.PLAIN,16));
        for(Student s:service.getStudents())
            out.append("Student ID: "+s.getId()+"\nName: "+s.getName()+"\nDepartment: "+s.getDepartment()+"\n--------------------------\n");
        if(service.getStudents().isEmpty()) out.setText("No students added yet.");
        JPanel buttons=new JPanel(); JButton update=new JButton("Update"), delete=new JButton("Delete");
        buttons.add(update); buttons.add(delete);
        update.addActionListener(e->updateStudent(out)); delete.addActionListener(e->deleteStudent(out));
        showListWindow("All Students",out,buttons);
    }

    private void updateStudent(JTextArea out) {
        Integer id=readId(this,"Enter Student ID to update:"); if(id==null)return;
        Student s=service.findStudent(id); if(s==null){JOptionPane.showMessageDialog(this,"Student not found!");return;}
        String name=JOptionPane.showInputDialog(this,"Enter new name:",s.getName()); if(name==null)return;
        String dept=JOptionPane.showInputDialog(this,"Enter new department:",s.getDepartment()); if(dept==null)return;
        s.setName(name); s.setDepartment(dept); JOptionPane.showMessageDialog(this,"Student updated successfully!");
    }
    private void deleteStudent(JTextArea out) {
        Integer id=readId(this,"Enter Student ID to delete:"); if(id==null)return;
        JOptionPane.showMessageDialog(this,service.deleteStudent(id)?"Student deleted successfully!":"Student not found!");
    }

    private void addTeacherDialog() {
        JTextField id=new JTextField(),name=new JTextField(),subject=new JTextField();
        JPanel p=new JPanel(new GridLayout(3,2)); p.add(new JLabel("Teacher ID:"));p.add(id);p.add(new JLabel("Teacher Name:"));p.add(name);p.add(new JLabel("Subject:"));p.add(subject);
        if(JOptionPane.showConfirmDialog(this,p,"Add Teacher",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            try{int x=Integer.parseInt(id.getText()); JOptionPane.showMessageDialog(this,service.addTeacher(x,name.getText(),subject.getText())?"Teacher added successfully!":"Teacher ID already exists!");}
            catch(NumberFormatException ex){JOptionPane.showMessageDialog(this,"Please enter a valid ID!");}
        }
    }
    private void showTeachers() {
        JTextArea out=new JTextArea();out.setEditable(false);out.setFont(new Font("Arial",Font.PLAIN,16));
        for(Teacher t:service.getTeachers()) out.append("Teacher ID: "+t.getId()+"\nName: "+t.getName()+"\nSubject: "+t.getSubject()+"\n--------------------------\n");
        if(service.getTeachers().isEmpty())out.setText("No teachers added yet.");
        JPanel b=new JPanel();JButton u=new JButton("Update"),d=new JButton("Delete");b.add(u);b.add(d);
        u.addActionListener(e->updateTeacher());d.addActionListener(e->{Integer id=readId(this,"Enter Teacher ID to delete:");if(id!=null)JOptionPane.showMessageDialog(this,service.deleteTeacher(id)?"Teacher deleted successfully!":"Teacher not found!");});
        showListWindow("All Teachers",out,b);
    }
    private void updateTeacher(){Integer id=readId(this,"Enter Teacher ID to update:");if(id==null)return;Teacher t=service.findTeacher(id);if(t==null){JOptionPane.showMessageDialog(this,"Teacher not found!");return;}String n=JOptionPane.showInputDialog(this,"Enter new name:",t.getName());if(n==null)return;String s=JOptionPane.showInputDialog(this,"Enter new subject:",t.getSubject());if(s==null)return;t.setName(n);t.setSubject(s);JOptionPane.showMessageDialog(this,"Teacher updated successfully!");}

    private void addCourseDialog() {
        JTextField id=new JTextField(),name=new JTextField(),teacher=new JTextField();
        JPanel p=new JPanel(new GridLayout(3,2));p.add(new JLabel("Course ID:"));p.add(id);p.add(new JLabel("Course Name:"));p.add(name);p.add(new JLabel("Teacher Name:"));p.add(teacher);
        if(JOptionPane.showConfirmDialog(this,p,"Add Course",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){try{int x=Integer.parseInt(id.getText());JOptionPane.showMessageDialog(this,service.addCourse(x,name.getText(),teacher.getText())?"Course added successfully!":"Course ID already exists!");}catch(NumberFormatException ex){JOptionPane.showMessageDialog(this,"Please enter a valid ID!");}}
    }
    private void showCourses() {
        JTextArea out=new JTextArea();out.setEditable(false);out.setFont(new Font("Arial",Font.PLAIN,16));
        for(Course c:service.getCourses())out.append("Course ID: "+c.getId()+"\nCourse Name: "+c.getName()+"\nTeacher: "+c.getTeacherName()+"\n--------------------------\n");
        if(service.getCourses().isEmpty())out.setText("No courses added yet.");
        JPanel b=new JPanel();JButton u=new JButton("Update"),d=new JButton("Delete");b.add(u);b.add(d);
        u.addActionListener(e->updateCourse());d.addActionListener(e->{Integer id=readId(this,"Enter Course ID to delete:");if(id!=null)JOptionPane.showMessageDialog(this,service.deleteCourse(id)?"Course deleted successfully!":"Course not found!");});
        showListWindow("All Courses",out,b);
    }
    private void updateCourse(){Integer id=readId(this,"Enter Course ID to update:");if(id==null)return;Course c=service.findCourse(id);if(c==null){JOptionPane.showMessageDialog(this,"Course not found!");return;}String n=JOptionPane.showInputDialog(this,"Enter new course name:",c.getName());if(n==null)return;String t=JOptionPane.showInputDialog(this,"Enter new teacher name:",c.getTeacherName());if(t==null)return;c.setName(n);c.setTeacherName(t);JOptionPane.showMessageDialog(this,"Course updated successfully!");}

    private void searchDialog() {
        String[] options={"Search Student","Search Teacher","Search Course"};
        int choice=JOptionPane.showOptionDialog(this,"What do you want to search?","Search",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if(choice<0)return; Integer id=readId(this,"Enter ID:");if(id==null)return;
        if(choice==0){Student s=service.findStudent(id);JOptionPane.showMessageDialog(this,s==null?"Student not found!":"Student ID: "+s.getId()+"\nName: "+s.getName()+"\nDepartment: "+s.getDepartment());}
        else if(choice==1){Teacher t=service.findTeacher(id);JOptionPane.showMessageDialog(this,t==null?"Teacher not found!":"Teacher ID: "+t.getId()+"\nName: "+t.getName()+"\nSubject: "+t.getSubject());}
        else{Course c=service.findCourse(id);JOptionPane.showMessageDialog(this,c==null?"Course not found!":"Course ID: "+c.getId()+"\nCourse Name: "+c.getName()+"\nTeacher: "+c.getTeacherName());}
    }

    private void showListWindow(String title,JTextArea out,JPanel buttons){
        JFrame w=new JFrame(title);w.setSize(500,450);w.setLocationRelativeTo(this);w.setLayout(new BorderLayout());w.add(new JScrollPane(out),BorderLayout.CENTER);w.add(buttons,BorderLayout.SOUTH);w.setVisible(true);
    }
}
