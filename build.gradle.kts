val userHomeDir: String = System.getProperty("user.home")

plugins {
    id("java")
}

subprojects {
    val projectName: String = name

    apply(plugin = "java")

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
        annotationProcessor("org.projectlombok:lombok:1.18.24")

        compileOnly("org.projectlombok:lombok:1.18.24")

        compileOnly("com.google.code.gson:gson:2.12.1")

        compileOnly(files("${userHomeDir}/EternalClient/Data/EternalClient.jar"))
    }

    tasks {
        if (path.contains("scripts:")) {
            jar {
                archiveFileName.set("$projectName.jar")
                destinationDirectory.set(file("$userHomeDir/EternalClient/Scripts"))
                from(sourceSets.main.get().output)
                from(configurations["library"].map { if (it.isDirectory) it else zipTree(it) })
            }
        }
        // Delete `build` folders
        subprojects.forEach { subproject -> delete(subproject.buildDir) }
        // Delete module `test` folders
        subprojects.forEach { subproject ->
            val testDir = File(subproject.projectDir, "src/test")
            if (testDir.exists()) {
                delete(testDir)
            }
        }
        // Delete module `resources` folders
        subprojects.forEach { subproject ->
            val resourcesDir = File(subproject.projectDir, "src/main/resources")
            if (resourcesDir.exists()) {
                delete(resourcesDir)
            }
        }
    }
}