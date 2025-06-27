//
// Created by richard on 27.06.25.
//

#include <GradeManager.h>
#include <android/binder_manager.h>
#include <android/binder_process.h>
#include <utils/Log.h>

using aidl::android::hardware::grademanager::GradeManager;

int main(int /* argc */, char* /* argv */[]) {
    ALOGI("Starting thread pool...");
    if (!ABinderProcess_setThreadPoolMaxThreadCount(0)) {
        ALOGE("%s", "failed to set thread pool max thread count");
        return 1;
    }
    ABinderProcess_startThreadPool();

    std::shared_ptr<GradeManager> grademanager =
            ::ndk::SharedRefBase::make<GradeManager>();

    ALOGI("Registering as service...");
    binder_exception_t err = AServiceManager_addService(
            grademanager->asBinder().get(), "android.hardware.grademanager.IGradeManager/default");
    if (err != EX_NONE) {
        ALOGE("failed to register android.hardware.grademanager service, exception: %d", err);
        return 1;
    }

    ALOGI("Grade Manager C++ Service Ready");

    ABinderProcess_joinThreadPool();

    ALOGI("Grade Manager Exiting");

    return 0;
}