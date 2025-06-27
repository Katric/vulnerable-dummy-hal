//! Dummy HAL with intentional security vulnerabilities and unwraps

use android_hardware_grademanager::aidl::android::hardware::grademanager::IGradeManager::BnGradeManager;
use binder::BinderFeatures;
use log::info;

mod grademanager;
use grademanager::GradeManagerService;

const LOG_TAG: &str = "grade_manager_service_rust";

use log::LevelFilter;

fn main() {
    let logger_success = logger::init(
        logger::Config::default()
            .with_tag_on_device(LOG_TAG)
            .with_max_level(LevelFilter::Trace),
    );
    if !logger_success {
        panic!("{LOG_TAG}: Failed to start logger.");
    }

    binder::ProcessState::set_thread_pool_max_thread_count(0);

    let grademanager_service = GradeManagerService::default();
    let grademanager_service_binder =
        BnGradeManager::new_binder(grademanager_service, BinderFeatures::default());

    let service_name = "android.hardware.grademanager.IGradeManager/default";
    binder::add_service(service_name, grademanager_service_binder.as_binder())
        .expect("Failed to register service");

    info!("Grade Manager Rust Service started!");

    binder::ProcessState::join_thread_pool()
}
