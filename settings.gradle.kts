pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PlantSeeds3"
include(":app")
include(":common")
include(":auth")
include(":seed")
include(":garden")
include(":data")
include(":domain")
include(":presentation")
