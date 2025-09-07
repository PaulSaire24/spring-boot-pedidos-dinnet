package com.dinnet.files.dto;

import java.util.Map;

public class SucessResponseDto {

    private int totalProcesados;
    private int guardados;
    private int conError;
    private String correlationId;
    private Map<String,Integer> errorDetails;

    public int getTotalProcesados() {
        return totalProcesados;
    }

    public void setTotalProcesados(int totalProcesados) {
        this.totalProcesados = totalProcesados;
    }

    public int getGuardados() {
        return guardados;
    }

    public void setGuardados(int guardados) {
        this.guardados = guardados;
    }

    public int getConError() {
        return conError;
    }

    public void setConError(int conError) {
        this.conError = conError;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Map<String, Integer> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(Map<String, Integer> errorDetails) {
        this.errorDetails = errorDetails;
    }
}
