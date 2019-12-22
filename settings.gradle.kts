
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
rootProject.name = "ddalivery-rider"

/***
 * ddalivery-api : API application
 * ddalivery-common : 도메인 & 고옹 사용 컴포넌트
 */
include ("ddalivery-rider","ddalivery-core","ddalivery-common")