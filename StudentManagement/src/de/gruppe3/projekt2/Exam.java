package de.gruppe3.projekt2;

class Exam {
    final float grade;
    final String subject;

    Exam(float grade, String subject) {
        this.grade = grade;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return subject + " (Grade: " + grade + ")";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Exam
                && ((Exam) obj).subject.equals(subject)
                && ((Exam) obj).grade == grade;
    }

    @Override
    public int hashCode() {
        return ((int) (grade * 100) << 16) + subject.hashCode() << 16;
    }
}
