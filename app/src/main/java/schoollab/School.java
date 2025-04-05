package schoollab;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Objects;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement
public class School {
  @XmlElement
  private int studentIdCounter;
  @XmlElement
  private List<Student> students;
  @XmlElement
  private List<Subject> subjects;
  private static Random random = new Random();

  public School() {
    this.students = new ArrayList<>();
    this.subjects = new ArrayList<>();
    studentIdCounter = 0;
  }

  public Student AddStudent(String Name, String LastName) {
    try {
      var student = new Student(Name, LastName, studentIdCounter);
      student.subjects = new ArrayList<>();
      for (Subject subject : subjects) {
        student.subjects.add(new Subject(subject.name));
      }
      students.add(student);
      studentIdCounter++;
      return student;
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public Student GetStudentById(int id) {
    for (Student student : students) {
      if (student.getId() == id) {
        return student;
      }
    }
    throw new IllegalArgumentException("Can't find student with that id");
  }

  public void UpdateStudentName(int id, String newName, String newLastName) {
    try {
      Student student = GetStudentById(id);
      student.name = newName;
      student.lastName = newLastName;
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    // Student student = GetStudentById(id);
  }

  public void RemoveStudent(int id) {
    try {
      Student student = GetStudentById(id);
      students.remove(student);
    } catch (IllegalArgumentException e) {
      System.out.println("Can't remove student with that id. Student wasn't found");
    }
  }

  // for safety returns copy and not pointer to main list
  public List<Student> GetAllStudents() {
    return List.copyOf(students);
  }

  public void AddSubject(String Name) {
    subjects.add(new Subject(Name));
    for (Student student : students) {
      Subject subject = new Subject(Name);
      student.subjects.add(subject);
    }
  }

  public Subject GetSubject(String Name) {
    for (Subject subject : subjects) {
      if (subject.name.equals(Name)) {
        return subject;
      }
    }
    throw new IllegalArgumentException("Subject with that name doesn't exist");
  }

  public void RemoveSubject(String Name) {
    try {
      Subject subject = GetSubject(Name);
      subjects.remove(subject);
      for (Student student : students) {
        student.subjects.remove(student.GetSubject(Name));
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  public void UpdateSubject(String Name, String newName) {
    Subject subject = GetSubject(Name);
    subject.name = newName;
    for (Student student : students) {
      student.GetSubject(Name).name = newName;
    }
  }

  public void WriteTest(String subjectName) {
    for (Student student : students) {
      student.WriteTest(subjectName, random.nextInt(13));
    }
  }

  public double GetPerfomance() {
    try {
      double sum = 0, studentCount = 0;
      if (!students.isEmpty()) {
        for (Student student : students) {
          double perfomance = student.GetPerfomance();
          if (perfomance != -1) {
            sum += perfomance;
            studentCount++;
          }
        }
        if (studentCount != 0) {
          return sum /= studentCount;
        }
        System.out.println("There is no marks of students in that School yet");
        return -1;
      } else {
        System.out.println("There is no students in that School yet");
        return -1;
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Couldn't calculate perfomance." + e.getMessage());
      return -1;
    }
  }

  public void PrintStudents() {
    for (Student student : students) {
      System.out.println(student.getId() + "  " + student.lastName + "  " + student.name);
    }
  }

  // TODO export and import

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    School school = (School) o;
    return studentIdCounter == school.studentIdCounter &&
        students.equals(school.students) &&
        subjects.equals(school.subjects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(studentIdCounter, students, subjects);
  }
}
