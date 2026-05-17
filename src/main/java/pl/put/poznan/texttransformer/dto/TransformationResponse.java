package pl.put.poznan.texttransformer.dto;

public class TransformationResponse {
    private String text;
    private String error;

    public TransformationResponse() {
    }

    public TransformationResponse(String text) {
        this.text = text;
    }

    public TransformationResponse(String text, String error) {
        this.text = text;
        this.error = error;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "TransformationResponse{" +
                "text='" + text + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
