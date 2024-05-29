val cdkVersion: String by project

plugins {
    application
    kotlin("jvm")
}

group = "com.campercontact"
version = "unspecified"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.campercontact.techradar.TechRadarApp")
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:$cdkVersion")
    implementation("software.constructs:constructs:[10.0.0,11.0.0)")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
}

kotlin {
    jvmToolchain(11)
}

tasks.test {
    useJUnitPlatform()
}
