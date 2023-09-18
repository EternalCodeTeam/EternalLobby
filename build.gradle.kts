import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "2.0.1"
}

group = "com.eternalcode"
version = "1.0.0"
val mainPackage = "com.eternalcode.lobby"

repositories {
    mavenCentral()
    gradlePluginPortal()

    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://repo.eternalcode.pl/releases") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://jitpack.io/") }
    maven { url = uri("https://repo.kryptonmc.org/releases") }
    maven { url = uri("https://repo.ranull.com/maven/external") }
    maven { url = uri("https://repo.unnamed.team/repository/unnamed-public/") }
    maven { url = uri("https://repository.minecodes.pl/releases") }
}

dependencies {
    // spigot-api
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")

    // a cool library, kyori
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit:2.8.9")

    // Cdn
    implementation("net.dzikoysk:cdn:1.14.3")

    // ExtendedClip
    compileOnly("me.clip:placeholderapi:2.11.2")

    // triumph gui
    implementation("dev.triumphteam:triumph-gui:3.1.4")

    // commons
    implementation("commons-io:commons-io:2.11.0")

    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.2")

    // liteskullapi
    implementation("dev.rollczi:liteskullapi:1.3.0")
}

bukkit {
    main = "$mainPackage.LobbyPlugin"
    name = "EternalLobby"
    apiVersion = "1.13"
    prefix = "EternalLobby"
    version = "${project.version}"
    author = "EternalCodeTeam"
    depend = listOf("PlaceholderAPI")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    runServer {
        minecraftVersion("1.20.1")
    }

    withType<ShadowJar> {
        archiveFileName.set("EternalLobby ${project.version}.jar")

        exclude(
                "org/intellij/lang/annotations/**",
                "org/jetbrains/annotations/**",
                "META-INF/**",
                "javax/**"
        )

        mergeServiceFiles()

        val prefix = "$mainPackage.libs"

        listOf(
                "panda",
                "org.panda_lang",
                "net.dzikoysk",
                "net.kyori",
                "dev.rollczi",
                "dev.triumphteam",
                "commons-io"
        ).forEach { relocate(it, prefix) }

    }


    getByName<Test>("test") {
        useJUnitPlatform()
    }

    withType<JavaCompile> {
        options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
        options.encoding = "UTF-8"
    }
}

