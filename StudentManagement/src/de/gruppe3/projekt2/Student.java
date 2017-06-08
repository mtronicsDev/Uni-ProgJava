package de.gruppe3.projekt2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Student {

    private String name;
    private String birthday;
    private int id;
    private Set<Exam> exams = new HashSet<>();
    private float avgGrade;

    /**
     * Constructs a new student.
     *
     * @param name     The student's validated name (first, second, ..., last divided by spaces)
     * @param birthday A String representing the student's birth date in the format dd.MM.yyy
     * @param id       The ID number.
     * @param examList A list containing the student's finished exams,
     *                 this list gets COPIED (i.e. can be reused without side effects)
     */
    Student(String name, String birthday, int id, List<Exam> examList) {
        this.name = name;
        this.birthday = birthday;
        this.id = id;
        exams.addAll(examList);

        calculateAvgGrade();
    }

    private void calculateAvgGrade() {
        // calculates the grade point average
        float total = 0;
        for (Exam exam : exams) {
            total += exam.grade;
        }
        avgGrade = total / exams.size();
    }

    String getName() {
        return name;
    }

    /**
     * Changes the name if the new one is valid
     *
     * @param name A valid name
     */
    void setName(String name) {
        if (Validator.valName.validate(name)) this.name = name;
    }

    String getBirthday() {
        return birthday;
    }

    /**
     * Changes the birthday if the new one is valid
     *
     * @param birthday A valid date
     */
    void setBirthday(String birthday) {
        if (Validator.valBirthday.validate(birthday)) this.birthday = birthday;
    }

    int getId() {
        return id;
    }

    /**
     * Changes the ID if the new one is valid
     *
     * @param id A valid ID
     */
    void setId(int id) {
        if (Validator.valID.validate(id)) this.id = id;
    }

    Set<Exam> getExams() {
        return exams;
    }

    float getAvgGrade() {
        return avgGrade;
    }

    /**
     * Nicely formats the sutdent's attributes into a string.
     *
     * @return The string...
     */
    @Override
    public String toString() {
        return name + "(BDay: " + birthday + ", Mat.-Nr: " + id + ", Avg. Grade: " + avgGrade + ")";
    }

    /**
     * Compares ids to check for equality.
     *
     * @param o The object to check for equality
     * @return true if equal, false if different
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Student
                && ((Student) o).getId() == id;
    }

    /**
     * Hash = id.
     *
     * @return The resulting hash code, die 2 most significant bytes sind ein Teil des birthday-Hashes,
     * die LSBs ein Teil des name-Hashes
     */
    @Override
    public int hashCode() {
        return id;
    }

    void addExam(Exam examToAdd) {
        exams.add(examToAdd);
        calculateAvgGrade();
    }
}
