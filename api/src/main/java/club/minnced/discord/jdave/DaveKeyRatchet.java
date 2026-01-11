package club.minnced.discord.jdave;

import club.minnced.discord.jdave.ffi.LibDaveKeyRatchetBinding;
import club.minnced.discord.jdave.ffi.NativeUtils;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

public class DaveKeyRatchet implements AutoCloseable {
    private final MemorySegment keyRatchet;

    public DaveKeyRatchet(@NonNull MemorySegment keyRatchet) {
        this.keyRatchet = keyRatchet;
    }

    @NonNull
    public static DaveKeyRatchet create(@NonNull DaveSessionImpl session, @NonNull String userId) {
        if (session.getProtocolVersion() == DaveConstants.DISABLED_PROTOCOL_VERSION) {
            return new DaveKeyRatchet(MemorySegment.NULL);
        }

        return new DaveKeyRatchet(session.getKeyRatchet(userId));
    }

    public boolean isNull() {
        return NativeUtils.isNull(keyRatchet);
    }

    @NonNull
    public MemorySegment getMemorySegment() {
        return keyRatchet;
    }

    @Override
    public void close() {
        if (!isNull()) {
            LibDaveKeyRatchetBinding.destroyKeyRatchet(this.keyRatchet);
        }
    }
}
