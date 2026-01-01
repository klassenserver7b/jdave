[![.github/workflows/build.yml](https://github.com/MinnDevelopment/jdave/actions/workflows/build.yml/badge.svg)](https://github.com/MinnDevelopment/jdave/actions/workflows/build.yml)

# Java Discord Audio & Video Encryption (JDAVE)

This library provides java bindings for [libdave](https://github.com/discord/libdave).

To implement these bindings, this library uses Java **Foreign Function & Memory API** (FFM).

## Requirements

- Java 25

## Supported Platforms

- Linux x64

## Example: JDA

To use this library with [JDA](https://github.com/discord-jda/JDA), you can use the [JDaveSessionFactory](src/main/java/club/minnced/discord/jdave/interop/JDaveSessionFactory.java) to configure the audio module:

```java
JDABuilder.createLight(TOKEN)
  .setAudioModuleConfig(new AudioModuleConfig()
    .withDaveSessionFactory(new JDaveSessionFactory()))
  .build()
```

---

# ⚠️ This implementation is work-in-progress and not completed.
