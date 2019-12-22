import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.3.21"
	id("org.jetbrains.kotlin.kapt") version "1.3.21"
	id("org.asciidoctor.convert") version "1.5.9.2"
	id("org.springframework.boot") version "2.1.4.RELEASE" apply false
	id("org.jetbrains.kotlin.plugin.spring") version "1.3.21" apply false
	id("com.gorylenko.gradle-git-properties") version "1.5.1" apply false
}

allprojects {
	repositories {
		// 요게 없으면 Cannot resolve external dependency org.jetbrains.kotlin:kotlin-compiler-embeddable:1.3.21 because no repositories are defined. 발생
		jcenter() // mavenCentral 인건 상관없네.
	}
}


subprojects {
	apply(plugin = "kotlin") // 요부분을 apply { plugin("kotlin")} -> apply(plugin="kotlin")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "org.asciidoctor.convert")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "com.gorylenko.gradle-git-properties")

	group = "lyj.ddalivery"
	version = "1.0.0"

	dependencies {
		compile("com.fasterxml.jackson.module:jackson-module-kotlin")
		compile("org.jetbrains.kotlin:kotlin-reflect")
		compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		compile("org.springframework.boot:spring-boot-starter-logging")
		compile("org.jetbrains.kotlin:kotlin-noarg:1.3.21")
		//Spring Boot
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-data-redis")
		implementation("org.springframework.boot:spring-boot-starter-hateoas")
		implementation("org.springframework.boot:spring-boot-starter-security")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("org.springframework.boot:spring-boot-starter-websocket")

		compile("org.projectlombok:lombok:1.18.4")

		/**
		 * @see <a href="https://kotlinlang.org/docs/reference/kapt.html">Annotation Processing with Kotlin</a>
		 */
		kapt("org.springframework.boot:spring-boot-configuration-processor")
		compileOnly("org.springframework.boot:spring-boot-configuration-processor")
//h2
		compile("com.h2database:h2")

		//Apache Commons
		compile("org.apache.commons:commons-lang3:3.8.1")
		compile("org.apache.commons:commons-collections4:4.2")
		compile("org.apache.commons:commons-compress:1.18")
		compile("org.apache.commons:commons-csv:1.6")
		compile("commons-net:commons-net:3.6")
		compile("commons-io:commons-io:2.6")
		compile("commons-codec:commons-codec:1.11")
		compile("org.apache.commons:commons-pool2:2.6.0")
		compile("commons-beanutils:commons-beanutils:1.9.3")


		// Utils
		compile("org.modelmapper:modelmapper:2.3.2")
		compile("com.google.guava:guava:27.0-jre")
		compile("eu.bitwalker:UserAgentUtils:1.21")
		compile("org.imgscalr:imgscalr-lib:4.2")

		//Compile Only
		compileOnly ("org.projectlombok:lombok")

		// Runtime
		runtimeOnly("mysql:mysql-connector-java")
		runtimeOnly("org.springframework.boot:spring-boot-devtools")

		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
		// Gradle 4.6 and later
		annotationProcessor("org.projectlombok:lombok")

		// Test
		testImplementation("io.projectreactor:reactor-test")
		testImplementation("org.springframework.security:spring-security-test")
		testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
		testImplementation("org.springframework.boot:spring-boot-starter-test")

		// Spring Rest Docs
		asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor:2.0.2.RELEASE")
		testCompile("org.springframework.restdocs:spring-restdocs-mockmvc")
		testCompile("io.rest-assured:rest-assured:3.2.0") // for rest assured
		testCompile("org.springframework.restdocs:spring-restdocs-restassured") // for rest assured

		testCompile("org.springframework.boot:spring-boot-starter-test")
	}

	tasks {
		compileKotlin {
			kotlinOptions {
				freeCompilerArgs = listOf("-Xjsr305=strict")
				jvmTarget = "1.8"
			}
			dependsOn(processResources) // kotlin 에서 ConfigurationProperties
		}


		compileTestKotlin {
			kotlinOptions {
				freeCompilerArgs = listOf("-Xjsr305=strict")
				jvmTarget = "1.8"
			}
		}
	}
}


project("ddalivery-common") {
	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true

	dependencies {
		compile(project(":ddalivery-core"))
	}

}

project("ddalivery-core") {
	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true
}

project("ddalivery-rider") {
	dependencies {
		compile(project(":ddalivery-core"))
		compile(project(":ddalivery-common"))

		runtime("org.springframework.boot:spring-boot-devtools")
	}
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
}
repositories {
	mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
	jvmTarget = "1.8"
}