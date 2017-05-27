import org.gradle.api.tasks.wrapper.Wrapper
import org.jetbrains.kotlin.gradle.dsl.*

group = "swak"
version = "0.1-SNAPSHOT"

createTask("wrapper", Wrapper::class) {
    gradleVersion = "3.5"
    distributionUrl = "https://services.gradle.org/distributions/gradle-$gradleVersion-all.zip"
}


buildscript {
    val kotlinVersion = "1.1.2-3"

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

subprojects {
    buildscript {
        repositories {
            jcenter()
            mavenCentral()
        }
    }

    repositories {
        jcenter()
        mavenCentral()
    }

    apply {
        plugin("kotlin")
    }

    tasks.withType<KotlinJvmCompile>{
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    dependencies {
        compile(kotlinModule("stdlib-jre8"))
    }
}