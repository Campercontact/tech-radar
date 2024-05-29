plugins {
    kotlin("jvm") version "1.9.22"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

kotlin {
    jvmToolchain(17)
}
