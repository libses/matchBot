package ru.urfu.bot;

public class InnerPhoto implements IPhotoSize {
    private String fileId;

    public String getFileId() {
        return fileId;
    }

    public InnerPhoto(String fileId) {
        this.fileId = fileId;
    }
}
