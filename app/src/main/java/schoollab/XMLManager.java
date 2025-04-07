package schoollab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class XMLManager {

    public static void ExportToXML(School school, String filePath, School.SortOption sortOption) {
        FileOutputStream fos = null; // Declare outside try for finally block
        try {
            fos = new FileOutputStream(filePath);
            try {
                JAXBContext context = JAXBContext.newInstance(School.class, Student.class, Subject.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                School schoolToExport;
                if (sortOption != null) {
                    schoolToExport = new School();
                    schoolToExport.subjects = new ArrayList<>(school.subjects); // Copy subjects list
                    schoolToExport.studentIdCounter = school.studentIdCounter; // Copy counter state

                    // Get the sorted list of students WITHOUT modifying the original school object
                    List<Student> sortedStudents = school.getSortedStudents(sortOption);
                    schoolToExport.students = sortedStudents; // Assign the sorted list to the temporary object
                } else {
                    // If no sorting is required, export the original school object directly
                    schoolToExport = school;
                }

                marshaller.marshal(schoolToExport, fos);
                System.out.println("Data successfully exported to: " + filePath
                        + (sortOption != null ? " (sorted by " + sortOption + ")" : ""));

            } catch (JAXBException e) {
                // Consider more specific logging or re-throwing a custom exception
                System.err.println("JAXB Error during XML export: " + e.getMessage());
                e.printStackTrace(); // Print stack trace for debugging
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found for export: " + filePath + " - " + e.getMessage());
        } finally {
            // ***BEST PRACTICE***: Ensure the FileOutputStream is closed
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.err.println("Error closing FileOutputStream: " + e.getMessage());
                }
            }
        }
    }
}
