package schoollab;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
  @XmlElement
  private int id;
  @XmlElement
  private String lastName;
  @XmlElement
  private String name;
  @XmlElement
  public List<Subject> subjects;

  public int getId() {
    return id;
  }

  public String GetName() {
    return name;
  }

  public String GetLastName() {
    return lastName;
  }

  public List<Subject> GetSubjects() {
    return subjects;
  }

  public Student() {
  }

  public Student(String Name, String LastName, int id) {
    if (Name == null || LastName == null) {
      throw new IllegalArgumentException("Cannot create Student with nullable name and last name");
    }
    this.name = Name;
    this.lastName = LastName;
    this.id = id;
    this.subjects = new ArrayList<>();
  }

  public Subject GetSubject(String Name) {
    for (Subject subject : subjects) {
      if (subject.name.equals(Name)) {
        return subject;
      }
    }
    throw new IllegalArgumentException("Subject with that name doesn't exist");
  }

  public void WriteTest(String subjectName, int mark) {
    Subject subject = GetSubject(subjectName);
    if (subject != null) {
      subject.AddMark(mark);
    } else {
      throw new IllegalArgumentException("Subject with that name doesn't exist");
    }
  }

  public double GetPerfomance() {
    double sum = 0, subjectsCount = 0;

    for (Subject subject : subjects) {
      double subjectMark = subject.GetAverageGrade();
      if (subjectMark != -1) {
        sum += subjectMark;
        subjectsCount++;
      }
    }
    if (subjectsCount == 0) {
      return -1;
    }
    return sum /= subjectsCount;
  }

  public void PrintMarks() {
    System.out.println(id + " " + lastName + " " + name);
    for (Subject subject : subjects) {
      System.out.print(subject.name + ": ");
      for (Integer mark : subject.marks) {
        System.out.print(mark + " ");
      }
      System.out.println();
    }
  }

  public void RemoveMark(String subjectName, Integer mark) {
    GetSubject(subjectName).RemoveMark(mark);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Student student = (Student) o;
    return id == student.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
