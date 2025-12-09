plugins {
    id 'fabric-loom' version '1.6.'+
    id 'maven-publish'
}

version = project.mod_version
group = project.maven_group

repositories {
    mavenCentral()
    maven { url = 'https://api.modrinth.com/maven' }
}

dependencies {
    minecraft "com.mojang:minecraft:${project.minecraft_version}"
    mappings loom.officialMojangMappings()
    modImplementation "net.fabricmc:fabric-loader:${project.loader_version}"
    modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
}

jar {
    from('LICENSE') {
        rename it, 'LICENSE_visualclient'
    }
}

publishingConfig = {
    github = true
}
