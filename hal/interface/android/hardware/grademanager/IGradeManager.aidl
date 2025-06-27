package android.hardware.grademanager;

@VintfStability
interface IGradeManager {
    // Return the grade of a student by index
    int getGrade(int studentIdx);

    // Set the grade of a student by index
    void setGrade(int studentIdx, int grade);

    // Clear all grades and set the reference of the array to null
    void clearGradesAndSetGradeListToNull();

    // Clear all grades and free the memory of the grade list
    void clearGradesAndFreeGradeList();

    // initialize a new grade list
    void initializeGradeList();
}