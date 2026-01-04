package jdave;

import static org.junit.jupiter.api.Assertions.assertEquals;

import club.minnced.discord.jdave.utils.NativeLibraryLoader;
import club.minnced.discord.jdave.utils.NativeLibraryLoader.NativeLibrary;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NativeLibraryLoaderTest {
    @ParameterizedTest
    @CsvSource({
        "Linux,x86_64,/natives/linux-x86-64/libdave.so",
        "Linux,x86,/natives/linux-x86/libdave.so",
        "Windows 10,x86_64,/natives/win-x86-64/dave.dll",
        "Windows 10,x86,/natives/win-x86/dave.dll",
        "MacOS Darwin,aarch64,/natives/darwin/libdave.dylib",
    })
    void resolveNativeLibrary(String osName, String archName, String resourcePath) {
        NativeLibrary library = NativeLibraryLoader.resolveLibrary("dave", osName, archName);

        assertEquals(resourcePath, library.resourcePath());
    }
}
