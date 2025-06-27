//
// Created by richard on 26.06.25.
//

#ifndef AOSP_PLAYGROUND_GRADEMANAGER_H
#define AOSP_PLAYGROUND_GRADEMANAGER_H

#include <aidl/android/hardware/grademanager/BnGradeManager.h>

namespace aidl {
    namespace android {
        namespace hardware {
            namespace grademanager {
                class GradeManager: public BnGradeManager {
                public:
                    GradeManager();

                    ndk::ScopedAStatus getGrade(int32_t studentIdx, int32_t* out) override;
                    ndk::ScopedAStatus setGrade(int32_t studentIdx, int32_t grade) override;
                    ndk::ScopedAStatus clearGradesAndSetGradeListToNull() override;
                    ndk::ScopedAStatus clearGradesAndFreeGradeList() override;
                    ndk::ScopedAStatus initializeGradeList() override;
                private:
                    int32_t* grades;
                };

            }
        }
    }
}

#endif //AOSP_PLAYGROUND_GRADEMANAGER_H
