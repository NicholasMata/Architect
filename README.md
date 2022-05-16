# Architect

A small code generating library, that generates boilerplate code for defining your Navigation Graph, setting up TopAppBar, and BottomNavigation in Jetpack Compose.

## Features

* Removes code duplication when describing your routes through out the application.
* Removes code for setting up bottom and top navigation.

## Setup

in your project level `build.gradle`
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}	
}
```
And then in you app level `build.gradle`

```groovy
dependencies { 
  // KSP processor
  ksp("com.github.nicholasmata.architect:compiler:1.0.0-alpha")

  implementation("com.github.nicholasmata.architect:annotations:1.0.0-alpha")
  implementation("com.github.nicholasmata.architect:core:1.0.0-alpha")
}

applicationVariants.all { variant ->
    kotlin.sourceSets {
        getByName(variant.name) {
            kotlin.srcDir("build/generated/ksp/${variant.name}/kotlin")
        }
    }
}
```

## Basic Usage

At the core of the `Architect` there is a `@Route` annotation. This annotation is used to describe your composable destination. Here is the basic setup for the Profile Screen

```kotlin
@Route(route = "profile", title = "Profile")
@Composable
fun ProfileScreen() {
    /** your screen */
}
```

After you build the project, `Architect` will generate multiple files based on the `@Route` annotations. These files now fully describe your Routes and can be used to describe you navigation graph easier and to navigate from one Route to another, and setup Tab bar.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Architect(
                tabs = Tab.values().toList(),
                screenGraph = ScreenGraph()
            )
        }
    }
}

enum class Tab(
    override val id: String,
    override val title: String,
    override val start: ArchitectScreen,
    override val icon: ImageVector,
    override var navState: Bundle = Bundle()
) : ArchitectTab {
    Profile(id = "profile", title = "Profile", start = Screen.Profile, icon = Icons.Default.Person)
}
```