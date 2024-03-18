package com.AltAir.celmembercard.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private String code;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public class Data {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("profile")
        @Expose
        private String profile;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("phoneNo")
        @Expose
        private String phoneNo;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("DOB")
        @Expose
        private String dob;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("validity")
        @Expose
        private String validity;
        @SerializedName("cardType")
        @Expose
        private String cardType;
        @SerializedName("cardicon")
        @Expose
        private String cardicon;
        @SerializedName("cardimage")
        @Expose
        private String cardimage;
        @SerializedName("memberid")
        @Expose
        private String memberid;
        @SerializedName("cardnumber")
        @Expose
        private String cardnumber;
        @SerializedName("dateofissue")
        @Expose
        private String dateofissue;
        @SerializedName("Qr")
        @Expose
        private String qr;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getValidity() {
            return validity;
        }

        public void setValidity(String validity) {
            this.validity = validity;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardicon() {
            return cardicon;
        }

        public void setCardicon(String cardicon) {
            this.cardicon = cardicon;
        }

        public String getCardimage() {
            return cardimage;
        }

        public void setCardimage(String cardimage) {
            this.cardimage = cardimage;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getCardnumber() {
            return cardnumber;
        }

        public void setCardnumber(String cardnumber) {
            this.cardnumber = cardnumber;
        }

        public String getDateofissue() {
            return dateofissue;
        }

        public void setDateofissue(String dateofissue) {
            this.dateofissue = dateofissue;
        }

        public String getQr() {
            return qr;
        }

        public void setQr(String qr) {
            this.qr = qr;
        }
//        @SerializedName("id")
//        @Expose
//        private Integer id;
//        @SerializedName("name")
//        @Expose
//        private String name;
//        @SerializedName("phoneNo")
//        @Expose
//        private String phoneNo;
//        @SerializedName("email")
//        @Expose
//        private String email;
//        @SerializedName("DOB")
//        @Expose
//        private String dob;
//        @SerializedName("gender")
//        @Expose
//        private String gender;
//        @SerializedName("city")
//        @Expose
//        private String city;
//        @SerializedName("validity")
//        @Expose
//        private String validity;
//        @SerializedName("image")
//        @Expose
//        private String image;
//        @SerializedName("cardType")
//        @Expose
//        private String cardType;
//        @SerializedName("memberid")
//        @Expose
//        private String memberid;
//        @SerializedName("cardnumber")
//        @Expose
//        private String cardnumber;
//        @SerializedName("dateofissue")
//        @Expose
//        private String dateofissue;
//        @SerializedName("Qr")
//        @Expose
//        private String Qr;
//        @SerializedName("profile")
//        @Expose
//        private String profile;
//        @SerializedName("cardImage")
//        @Expose
//        private String cardImage;
//        @SerializedName("cardicon")
//        @Expose
//        private String cardicon;

//        public String getCardicon() {
//            return cardicon;
//        }
//
//        public void setCardicon(String cardicon) {
//            this.cardicon = cardicon;
//        }
//
//        public String getCardImage() {
//            return cardImage;
//        }
//
//        public void setCardImage(String cardImage) {
//            this.cardImage = cardImage;
//        }
//
//        public String getQr() {
//            return Qr;
//        }
//
//        public void setQr(String qr) {
//            Qr = qr;
//        }
//
//        public String getProfile() {
//            return profile;
//        }
//
//        public void setProfile(String profile) {
//            this.profile = profile;
//        }
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getPhoneNo() {
//            return phoneNo;
//        }
//
//        public void setPhoneNo(String phoneNo) {
//            this.phoneNo = phoneNo;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getDob() {
//            return dob;
//        }
//
//        public void setDob(String dob) {
//            this.dob = dob;
//        }
//
//        public String getGender() {
//            return gender;
//        }
//
//        public void setGender(String gender) {
//            this.gender = gender;
//        }
//
//        public String getCity() {
//            return city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        public String getValidity() {
//            return validity;
//        }
//
//        public void setValidity(String validity) {
//            this.validity = validity;
//        }
//
//        public String getImage() {
//            return image;
//        }
//
//        public void setImage(String image) {
//            this.image = image;
//        }
//
//        public String getCardType() {
//            return cardType;
//        }
//
//        public void setCardType(String cardType) {
//            this.cardType = cardType;
//        }
//
//        public String getMemberid() {
//            return memberid;
//        }
//
//        public void setMemberid(String memberid) {
//            this.memberid = memberid;
//        }
//
//        public String getCardnumber() {
//            return cardnumber;
//        }
//
//        public void setCardnumber(String cardnumber) {
//            this.cardnumber = cardnumber;
//        }
//
//        public String getDateofissue() {
//            return dateofissue;
//        }
//
//        public void setDateofissue(String dateofissue) {
//            this.dateofissue = dateofissue;
//        }
    }
}
