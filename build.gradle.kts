import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.ktlint)
}

group = "org.bizilabs.apps.shaker"
version = System.getenv("VERSION") ?: "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    // compose
    implementation(compose.desktop.currentOs)
    implementation(compose.components.resources)
    // material
    implementation(compose.materialIconsExtended)
    implementation(compose.material3)
    // coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.swing)
    // voyager
    implementation(libs.bundles.voyager)
    // compottie
    implementation(libs.bundles.compottie)
}

compose.desktop {
    application {
        mainClass = "org.bizilabs.apps.shaker.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "shaker"
            packageVersion = System.getenv("VERSION") ?: "1.0.0"
            macOS {
                iconFile.set(project.file("${project.rootDir}/icons/icon.icns"))
            }
            windows {
                iconFile.set(project.file("${project.rootDir}/icons/icon.ico"))
            }
            linux {
                iconFile.set(project.file("${project.rootDir}/icons/icon.png"))
            }
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "org.bizilabs.apps.shaker.resources"
    generateResClass = auto
    customDirectory(
        sourceSetName = "main",
        directoryProvider = provider { layout.projectDirectory.dir("src/main/resources") },
    )
}
