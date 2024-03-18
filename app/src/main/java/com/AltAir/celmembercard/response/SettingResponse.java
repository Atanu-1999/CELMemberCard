package com.AltAir.celmembercard.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingResponse {
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("version")
        @Expose
        private String version;
        @SerializedName("maintenance")
        @Expose
        private String maintenance;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getMaintenance() {
            return maintenance;
        }

        public void setMaintenance(String maintenance) {
            this.maintenance = maintenance;
        }
    }
}
