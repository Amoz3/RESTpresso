plugins {
    id("java")
}



subprojects {
    val projectName: String = name

    apply(plugin = "java")
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }

    repositories {
        mavenCentral()
    }


    configurations {
        create("library")
        get("implementation").apply {
            extendsFrom(configurations["library"])
        }
    }


    dependencies {
        implementation(group = "com.google.code.gson", name = "gson", version = "2.8.9")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        compileOnly("org.projectlombok:lombok:1.18.24")
    }
}
dependencies {
    implementation("org.projectlombok:lombok:1.18.28")
}
