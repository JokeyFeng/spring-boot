package com.jokey.base;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.base
 * comment:
 */
public class DbConfig {
    /**
     * total header data block size
     */
    private int totalHeaderSize;

    /**
     * max index data block size
     * u should always choice the fastest read block size
     */
    private int indexBlockSize;

    /**
     * construct method
     *
     * @param totalHeaderSize
     */
    public DbConfig(int totalHeaderSize) {
        if ((totalHeaderSize % 8) != 0) {
            throw new RuntimeException("totalHeaderSize must be times of 8");
        }

        this.totalHeaderSize = totalHeaderSize;
        this.indexBlockSize = 8192;
    }

    public DbConfig() {
        this(8 * 2048);
    }

    public int getTotalHeaderSize() {
        return totalHeaderSize;
    }

    public DbConfig setTotalHeaderSize(int totalHeaderSize) {
        this.totalHeaderSize = totalHeaderSize;
        return this;
    }

    public int getIndexBlockSize() {
        return indexBlockSize;
    }

    public DbConfig setIndexBlockSize(int dataBlockSize) {
        this.indexBlockSize = dataBlockSize;
        return this;
    }
}
