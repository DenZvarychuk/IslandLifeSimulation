package main.java.org.island.playground.generators;

public class RandomGenerator {
    public static double generate(int x, int y, long seed) {
        long n = x * 374761393L + y * 668265263L + seed;
        n ^= n >> 13;
        n *= 1274126177L;
        n ^= n >> 16;
        return (n & 0x7fffffff) / 2147483648.0;
    }
}
