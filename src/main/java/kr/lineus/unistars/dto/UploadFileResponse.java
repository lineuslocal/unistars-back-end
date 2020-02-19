package kr.lineus.unistars.dto;

import org.springframework.http.MediaType;

public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
	private String imageUri = "";
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
		if (MediaType.IMAGE_JPEG_VALUE.equalsIgnoreCase(fileType)) {
			imageUri = fileDownloadUri.replace("downloadFile", "viewJPEG");
		}
		if (MediaType.IMAGE_PNG_VALUE.equalsIgnoreCase(fileType)) {
			imageUri = fileDownloadUri.replace("downloadFile", "viewPNG");
		}
		if (MediaType.IMAGE_GIF_VALUE.equalsIgnoreCase(fileType)) {
			imageUri = fileDownloadUri.replace("downloadFile", "viewGIF");
		}
    }

	public String getFileName() {
		return fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public long getSize() {
		return size;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}
}