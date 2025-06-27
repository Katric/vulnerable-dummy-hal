//
// Created by richard on 27.06.25.
//
#define LOG_TAG "GradeManagerCpp"

#include "GradeManager.h"
#include <utils/Log.h>

namespace aidl {
    namespace android {
        namespace hardware {
            namespace grademanager {

                GradeManager::GradeManager() {
                    grades = new int[30];  
                }

                ndk::ScopedAStatus GradeManager::getGrade(int32_t studentIdx, int32_t* out) {
                    *out = grades[studentIdx];
                    return ndk::ScopedAStatus::ok();
                }

                ndk::ScopedAStatus GradeManager::setGrade(int32_t studentIdx, int32_t grade) {
                    grades[studentIdx] = grade;
                    return ndk::ScopedAStatus::ok();
                }

                ndk::ScopedAStatus GradeManager::clearGradesAndSetGradeListToNull() {
                    grades = nullptr;
                    return ndk::ScopedAStatus::ok();
                }

                ndk::ScopedAStatus GradeManager::clearGradesAndFreeGradeList() {
                    delete[] grades;
                    return ndk::ScopedAStatus::ok();
                }

                ndk::ScopedAStatus GradeManager::initializeGradeList() {
                    grades = new int[30];
                    return ndk::ScopedAStatus::ok();
                }
            }
        }
    }
}
