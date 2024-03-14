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

rootProject.name = "Resume"
include(":app")
include(":foundation:data")
include(":foundation:presentation")
include(":feature:home:data")
include(":feature:home:presentation")
include(":feature:home:domain")
include(":foundation:domain")
