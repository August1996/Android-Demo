package com.lalongooo.videocompressor;

import android.media.MediaMetadataRetriever;

/**
 * Created by August on 16/8/11.
 * 存放媒体信息
 */
public class MediaInfo {
    private int bitrate;
    private int width;
    private int height;
    private int rotation;
    private long duration;

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public static MediaInfo getMediaInfo(String src) {
        MediaInfo mediaInfo = new MediaInfo();

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(src);

        mediaInfo.setBitrate(Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)));
        mediaInfo.setWidth(Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)));
        mediaInfo.setHeight(Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)));
        mediaInfo.setRotation(Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)));
        mediaInfo.setDuration(Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));

        retriever.release();
        return mediaInfo;
    }
}
