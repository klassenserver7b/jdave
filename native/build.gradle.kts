import de.undercouch.gradle.tasks.download.Download
import java.nio.file.Files
import jdave.gradle.Architecture
import jdave.gradle.OperatingSystem
import jdave.gradle.getPlatform

plugins {
    `publishing-environment`
    alias(libs.plugins.download)
}

publishingEnvironment { moduleName = "jdave-native-${getPlatform()}" }

dependencies {
    api(project(":api"))

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.logback)
}

fun getArtifactName(): String {
    return "libdave.so"
}

fun getArtifactDownloadUrl(): String {
    return "https://github.com/klassenserver7b/jdave/releases/download/1.5/${getArtifactName()}"
}

val nativeResourceRoot = "resources/libdave"
val downloadDir: Provider<Directory> = layout.buildDirectory.dir("downloads")

val downloadNatives by
    tasks.registering(Download::class) {
        doFirst { Files.createDirectories(downloadDir.get().asFile.toPath()) }

        src(getArtifactDownloadUrl())
        dest(downloadDir)
    }

val assembleNatives by
    tasks.registering(Copy::class) {
        dependsOn(downloadNatives)

        from(downloadDir.get().file(getArtifactName())) {
            include {
                it.name.endsWith(".so") || it.name.endsWith(".dll") || it.name.endsWith(".dylib")
            }
        }

        into(layout.buildDirectory.dir("$nativeResourceRoot/natives/${getPlatform()}"))
    }

tasks.processResources {
    dependsOn(assembleNatives)
    from(layout.buildDirectory.dir(nativeResourceRoot))
}

tasks.test {
    useJUnitPlatform()

    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")

    testLogging { events("passed", "skipped", "failed") }

    reports {
        junitXml.required = true
        html.required = true
    }
}
