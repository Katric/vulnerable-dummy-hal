use android_hardware_grademanager::aidl::android::hardware::grademanager::IGradeManager::IGradeManager;
use binder::{ExceptionCode, Interface, Status};
use log::info;
use std::sync::Mutex;

pub struct GradeManagerService {
    grades: Mutex<Option<[i32; 30]>>,
}

impl Interface for GradeManagerService {}

impl Default for GradeManagerService {
    fn default() -> Self {
        Self {
            grades: Mutex::new(Some([100; 30])),
        }
    }
}

#[allow(non_snake_case)]
impl IGradeManager for GradeManagerService {
    fn getGrade(&self, studentIdx: i32) -> binder::Result<i32> {
        info!("Retrieving grade of student {}", studentIdx);
        let locked_grades = self.grades.lock().unwrap().unwrap();
        Ok(locked_grades[studentIdx as usize])
    }

    fn setGrade(&self, studentIdx: i32, grade: i32) -> binder::Result<()> {
        info!("Setting grade of student {} to {}", studentIdx, grade);
        let mut locked_grades = self.grades.lock().unwrap().unwrap();
        locked_grades[studentIdx as usize] = grade;
        Ok(())
    }

    fn clearGradesAndSetGradeListToNull(&self) -> binder::Result<()> {
        *self.grades.lock().unwrap() = None;
        Ok(())
    }

    // Clear all grades and free the memory of the grade list
    fn clearGradesAndFreeGradeList(&self) -> binder::Result<()> {
        Err(Status::new_exception(
            ExceptionCode::UNSUPPORTED_OPERATION,
            None,
        ))
    }

    fn initializeGradeList(&self) -> binder::Result<()> {
        *self.grades.lock().unwrap() = Some([100; 30]);
        Ok(())
    }
}
