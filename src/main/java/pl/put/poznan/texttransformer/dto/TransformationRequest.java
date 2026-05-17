package pl.put.poznan.texttransformer.dto;

import java.util.Arrays;

public class TransformationRequest {
    private String text;
    private String[] transformation;

    public TransformationRequest() {
    }

    public TransformationRequest(String text, String[] transformation) {
        this.text = text;
        this.transformation = transformation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getTransformation() {
        return transformation;
    }

    public void setTransformation(String[] transformation) {
        this.transformation = transformation;
    }

    @Override
    public String toString() {
        return "TransformationRequest{" +
                "text='" + text + '\'' +
                ", transformation=" + Arrays.toString(transformation) +
                '}';
    }
}
