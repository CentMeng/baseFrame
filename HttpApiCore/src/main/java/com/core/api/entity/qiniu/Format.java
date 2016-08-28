package com.core.api.entity.qiniu;

import com.core.api.entity.BaseEntity;

/**
 * @author CentMeng csdn@vip.163.com on 15/11/17.
 */
public class Format extends BaseEntity {

    private String filename;

    private int nb_streams;

    private String format_name;

    private String format_long_name;

    private long start_time;

    private float duration;

    private int size;

    private int bit_rate;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getNb_streams() {
        return nb_streams;
    }

    public void setNb_streams(int nb_streams) {
        this.nb_streams = nb_streams;
    }

    public String getFormat_name() {
        return format_name;
    }

    public void setFormat_name(String format_name) {
        this.format_name = format_name;
    }

    public String getFormat_long_name() {
        return format_long_name;
    }

    public void setFormat_long_name(String format_long_name) {
        this.format_long_name = format_long_name;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBit_rate() {
        return bit_rate;
    }

    public void setBit_rate(int bit_rate) {
        this.bit_rate = bit_rate;
    }
}
