package schoollab;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Subject {
  @XmlElement
  public List<Integer> marks;
  @XmlElement
  public String name;

  public double GetAverageGrade() {
    double sum = 0;
    for (Integer mark : marks) {
      sum += mark;
    }
    if (marks.size() == 0) {
      return -1;
    }
    return sum /= marks.size();
  }

  public Subject() {
  }

  public Subject(String Name) {
    marks = new ArrayList<>();
    name = Name;
  }

  public void AddMark(int mark) {
    if (mark <= 12 && mark >= 0) {
      marks.add(mark);
    } else {
      throw new IllegalArgumentException("Mark is not in the valid range(it has to be from 0 to 12)");
    }
  }

  public void RemoveMark(int mark) {
    int index = marks.lastIndexOf(mark);
    if (index == -1) {
      throw new IllegalArgumentException(
          "There is no " + mark + " in the marks of this Student of " + name + " subject");
    } else {
      marks.remove(index);
    }
  }
}
