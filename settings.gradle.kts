pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "kasip-kz"
include(":app")
include(":onboarding")
include(":data")
include(":designcore")
include(":settings")
include(":rialto")
include(":order")
include(":chat")
include(":works")
include(":response")
include(":catalog")
include(":profile")
