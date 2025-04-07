package schoollab;

import java.util.ArrayList;
import java.util.Comparator;
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
    var student = new Student(Name, LastName, studentIdCounter);
    student.subjects = new ArrayList<>();
    for (Subject subject : subjects) {
      student.subjects.add(new Subject(subject.name));
    }
    students.add(student);
    studentIdCounter++;
    return student;
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
    if (newName == null || newLastName == null) {
      throw new IllegalArgumentException("Name can't be null");
    }
    Student student = GetStudentById(id);
    student.setName(newName);
    student.setLastName(newLastName);
    // Student student = GetStudentById(id);
  }

  // System.out.println("Can't remove student with that id. Student wasn't
  // found");
  public void RemoveStudent(int id) {
    Student student = GetStudentById(id);
    students.remove(student);

  }

  // for safety returns copy and not pointer to main list
  public List<Student> GetAllStudents() {
    return List.copyOf(students);
  }

  public void AddSubject(String Name) {
    for (Subject subject : subjects) {
      if (Name == subject.name) {
        throw new IllegalArgumentException("There is already this subject");
      }
    }
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
    Subject subject = GetSubject(Name);
    subjects.remove(subject);
    for (Student student : students) {
      student.subjects.remove(student.GetSubject(Name));
    }
  }

  public void UpdateSubject(String Name, String newName) {
    for (Subject subject : subjects) {
      if (Name == subject.name) {
        throw new IllegalArgumentException("There is already subject with that name");
      }
    }
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
      throw new IllegalArgumentException("There is no marks of students in that School yet");
    } else {
      throw new IllegalArgumentException("There is no students in that School yet");
    }
  }

  public void PrintStudents() {
    for (Student student : students) {
      System.out.println(student.getId() + "  " + student.getLastName() + "  " + student.getName());
    }
  }

  // TODO export and import

  public enum SortOption {
    LAST_NAME,
    STUDENT_ID,
    PERFORMANCE
  }

  public List<Student> getSortedStudents(SortOption sortOption) {
    List<Student> sortedStudents = new ArrayList<>(students);
    switch (sortOption) {
      case LAST_NAME:
        sortedStudents.sort(Comparator.comparing(Student::getLastName));
        break;
      case STUDENT_ID:
        sortedStudents.sort(Comparator.comparingInt(Student::getId));
        break;
      case PERFORMANCE:
        sortedStudents.sort(Comparator.comparingDouble(Student::GetPerfomance));
        break;
      default:
        break;
    }
    return sortedStudents;
  }

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
