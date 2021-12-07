package ru.urfu.bot;

/**
 * Класс фотографий, используемый внутри логики самого бота
 */
public class InnerPhoto implements IPhotoSize {
    private final String fileId;

    public String getFileId() {
        return fileId;
    }

    public InnerPhoto(String fileId) {
        this.fileId = fileId;
    }
}
