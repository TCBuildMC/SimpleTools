# SimpleTools

A java library provides some common utilities and tools.

## Add dependency
[![](https://jitpack.io/v/TCBuildMC/SimpleTools.svg)](https://jitpack.io/#TCBuildMC/SimpleTools)

Add these to your `build.gradle`:
```gradle
repositories {
    maven {
        url = "https://jitpack.io/"
    }
}

dependencies {
    implementation "com.github.TCBuildMC:SimpleTools:{VERSION}"
}
```

Remember to replace `{VERSION}` with full version.

## Building
This project is compatible with Java 8 at least, so please use Java 8 to build.

We also prefer to use IntelliJ IDEA to build.

Download the zip and run `gradlew build --stacktrace --no-daemon -xtest`.

The artifacts will be in `build/libs/` directory.

## License
This project uses Apache 2.0 License. Feel free to use it in your project.

