package me.redtea.carcadex.reload.exception;

public class SimpledReloaderException extends RuntimeException {
    public SimpledReloaderException() {
        super("Simpled reloader is not implements this operation. Please use classic reloader");
    }
}
