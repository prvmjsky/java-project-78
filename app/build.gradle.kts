plugins {
    application
    checkstyle
    jacoco
    id("org.sonarqube") version "6.0.1.5171"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

sonar {
    properties {
        property("sonar.projectKey", "prvmjsky_java-project-78")
        property("sonar.organization", "prvmjsky")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

application {
    mainClass = "hexlet.code.App"
}

tasks.jacocoTestReport {
    reports {
        dependsOn(tasks.test)
        xml.required.set(true)
    }
}
